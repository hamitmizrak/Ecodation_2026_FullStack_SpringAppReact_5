package com.hamitmizrak.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamitmizrak.business.dto.BlogDto;
import com.hamitmizrak.business.services.impl.BlogServicesImpl;
import com.hamitmizrak.business.services.interfaces.IBlogServices;
import com.hamitmizrak.controller.interfaces.IBlogApi;
import com.hamitmizrak.error.ApiResult;
import com.hamitmizrak.file_upload.FileProps;
import com.hamitmizrak.utily.FrontEnd;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

// Lombok
@RequiredArgsConstructor
@Log4j2

// Api
@RestController
@RequestMapping("/blog/api/v1.0.0")
@CrossOrigin(origins = FrontEnd.REACT_URL)
public class BlogApiImpl implements IBlogApi<BlogDto> {

    private static final long MAX_IMAGE_SIZE = 10L * 1024L * 1024L;
    private static final Set<String> ALLOWED_IMAGE_EXTENSIONS = Set.of(
            "jpg", "jpeg", "png", "gif", "webp", "bmp", "svg"
    );

    private static final String SQL_SELECT_ALL_IMAGES =
            "SELECT BLOG_ID, IMAGE FROM BLOGS";
    private static final String SQL_SELECT_IMAGE_BY_ID =
            "SELECT IMAGE FROM BLOGS WHERE BLOG_ID = ?";
    private static final String SQL_UPDATE_IMAGE_BY_ID =
            "UPDATE BLOGS SET IMAGE = ? WHERE BLOG_ID = ?";
    private static final String SQL_FIND_LAST_MATCHING_ID =
            "SELECT MAX(BLOG_ID) FROM BLOGS WHERE HEADER = ? AND TITLE = ?";

    // Field
    private final BlogServicesImpl iblogservices;
    private final ObjectMapper objectMapper;
    private final IBlogServices iBlogServices;
    private final FileProps fileProps;
    private final JdbcTemplate jdbcTemplate;

    /// /////////////////////////////////////
    /// SPEED DATA
    @Override
    @GetMapping("speed")
    public ResponseEntity<ApiResult<List<BlogDto>>> speedData(Integer data) {
        return null;
    }

    /// DELETE ALL
    @Override
    @GetMapping("delete-all")
    public ResponseEntity<ApiResult<List<BlogDto>>> deleteData() {
        return null;
    }

    /// /////////////////////////////////////
    /// CREATE (RESİMSİZ / URL İLE) JSON
    @Override
    @Transactional
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<?>> objectApiCreate(@Valid @RequestBody BlogDto blogDto) {
        try {
            normalizeDtoImage(blogDto);

            Object created = iblogservices.objectServiceCreate(blogDto);
            Long createdId = resolveBlogId(created, blogDto);

            if (hasText(blogDto.getImage())) {
                requireBlogId(createdId, "Blog oluşturuldu ancak görsel yolunu yazmak için kayıt ID'si alınamadı.");
                updateImageColumn(createdId, blogDto.getImage());
            }

            enrichResultImage(created);
            return ResponseEntity.ok(ApiResult.success(created));
        } catch (Exception ex) {
            log.error("Blog JSON create hatası", ex);
            return ResponseEntity.ok(ApiResult.error(
                    "serverError", safeMessage(ex), "/blog/api/v1.0.0/create"
            ));
        }
    }

    /// CREATE (RESİMLİ) MULTIPART
    /// FormData alanları: blog=<JSON string>, file=<image>
    @Override
    @Transactional
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<?>> objectServiceCreateWithFile(
            @RequestPart("blog") String json,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile) {

        String storedImagePath = null;

        try {
            BlogDto blogDto = objectMapper.readValue(json, BlogDto.class);
            normalizeDtoImage(blogDto);

            if (hasFile(multipartFile)) {
                storedImagePath = storeBlogImage(multipartFile);
                blogDto.setImage(storedImagePath);
            }

            Object created = iblogservices.objectServiceCreate(blogDto);
            Long createdId = resolveBlogId(created, blogDto);

            if (hasText(blogDto.getImage())) {
                requireBlogId(createdId, "Blog oluşturuldu ancak IMAGE alanını güncellemek için kayıt ID'si alınamadı.");
                updateImageColumn(createdId, blogDto.getImage());
            }

            enrichResultImage(created);
            return ResponseEntity.ok(ApiResult.success(created));
        } catch (Exception ex) {
            deleteStoredImageQuietly(storedImagePath);
            log.error("Blog multipart create hatası", ex);
            return ResponseEntity.ok(ApiResult.error(
                    "serverError", safeMessage(ex), "/blog/api/v1.0.0/create"
            ));
        }
    }

    // LIST
    @Override
    @GetMapping("/list")
    public ResponseEntity<ApiResult<List<BlogDto>>> objectApiList() {
        try {
            List<BlogDto> list = iblogservices.objectServiceList();
            enrichListImages(list);
            return ResponseEntity.ok(ApiResult.success(list));
        } catch (Exception ex) {
            log.error("Blog list hatası", ex);
            return ResponseEntity.ok(ApiResult.error(
                    "serverError", safeMessage(ex), "/blog/api/v1.0.0/list"
            ));
        }
    }

    // FIND
    @Override
    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResult<?>> objectApiFindById(@PathVariable(name = "id") Long id) {
        try {
            Object found = iblogservices.objectServiceFindById(id);
            enrichResultImage(found);
            return ResponseEntity.ok(ApiResult.success(found));
        } catch (Exception ex) {
            log.error("Blog find hatası. id={}", id, ex);
            return ResponseEntity.ok(ApiResult.error(
                    "serverError", safeMessage(ex), "/blog/api/v1.0.0/find/" + id
            ));
        }
    }

    // UPDATE (RESİMSİZ / MEVCUT RESMİ KORUYAN) JSON
    @Override
    @Transactional
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<?>> objectApiUpdate(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody BlogDto blogDto) {
        try {
            String currentImage = readImageById(id);
            normalizeDtoImage(blogDto);

            // Frontend boş image gönderirse mevcut resmi silme.
            String finalImage = hasText(blogDto.getImage()) ? blogDto.getImage() : currentImage;
            blogDto.setImage(finalImage);
            blogDto.setBlogId(id);

            Object updated = iblogservices.objectServiceUpdate(id, blogDto);

            if (hasText(finalImage)) {
                updateImageColumn(id, finalImage);
            }

            enrichResultImage(updated);
            return ResponseEntity.ok(ApiResult.success(updated));
        } catch (Exception ex) {
            log.error("Blog JSON update hatası. id={}", id, ex);
            return ResponseEntity.ok(ApiResult.error(
                    "serverError", safeMessage(ex), "/blog/api/v1.0.0/update/" + id
            ));
        }
    }

    // UPDATE (RESİMLİ) MULTIPART
    @Override
    @Transactional
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<?>> objectServiceUpdateWithFile(
            @PathVariable(name = "id") Long id,
            @RequestPart("blog") String json,
            @RequestPart(value = "file", required = false) MultipartFile multipartFile) {

        String newlyStoredImagePath = null;

        try {
            String currentImage = readImageById(id);
            BlogDto blogDto = objectMapper.readValue(json, BlogDto.class);
            normalizeDtoImage(blogDto);

            String finalImage;
            if (hasFile(multipartFile)) {
                newlyStoredImagePath = storeBlogImage(multipartFile);
                finalImage = newlyStoredImagePath;
            } else if (hasText(blogDto.getImage())) {
                finalImage = blogDto.getImage();
            } else {
                finalImage = currentImage;
            }

            blogDto.setBlogId(id);
            blogDto.setImage(finalImage);

            Object updated = iblogservices.objectServiceUpdate(id, blogDto);

            if (hasText(finalImage)) {
                updateImageColumn(id, finalImage);
            }

            // DB işlemi başarılı olduktan sonra eski yerel görseli temizle.
            if (hasText(newlyStoredImagePath)
                    && hasText(currentImage)
                    && !currentImage.equals(newlyStoredImagePath)) {
                deleteStoredImageQuietly(currentImage);
            }

            enrichResultImage(updated);
            return ResponseEntity.ok(ApiResult.success(updated));
        } catch (Exception ex) {
            deleteStoredImageQuietly(newlyStoredImagePath);
            log.error("Blog multipart update hatası. id={}", id, ex);
            return ResponseEntity.ok(ApiResult.error(
                    "serverError", safeMessage(ex), "/blog/api/v1.0.0/update/" + id
            ));
        }
    }

    // DELETE
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResult<?>> objectApiDelete(@PathVariable("id") Long id) {
        try {
            String currentImage = readImageById(id);
            BlogDto deleteBlogDto = (BlogDto) iBlogServices.objectServiceDelete(id);
            deleteStoredImageQuietly(currentImage);
            return ResponseEntity.ok(ApiResult.success(deleteBlogDto));
        } catch (Exception ex) {
            log.error("Blog delete hatası. id={}", id, ex);
            return ResponseEntity.ok(ApiResult.error(
                    "serverError", safeMessage(ex), "/blog/api/v1.0.0/delete/" + id
            ));
        }
    }

    /// ///////////////////////////////////////////////////////////////////////////
    /// IMAGE - DATABASE SYNCHRONIZATION
    /// ///////////////////////////////////////////////////////////////////////////

    /**
     * Service/mapper image alanını entity'ye taşımıyor olsa bile
     * BLOGS.IMAGE kolonunu kesin olarak günceller.
     */
    private void updateImageColumn(Long blogId, String imagePath) {
        requireBlogId(blogId, "Görsel kaydı için blog ID zorunludur.");

        String normalized = normalizeImagePath(imagePath);
        int affected = jdbcTemplate.update(SQL_UPDATE_IMAGE_BY_ID, normalized, blogId);

        if (affected != 1) {
            throw new IllegalStateException(
                    "BLOGS.IMAGE güncellenemedi. Etkilenen kayıt sayısı: " + affected + ", blogId=" + blogId
            );
        }

        log.info("BLOGS.IMAGE güncellendi. blogId={}, image={}", blogId, normalized);
    }

    /**
     * Service/entity -> DTO mapper image alanını taşımıyor olsa bile
     * liste cevaplarını DB'deki gerçek IMAGE değeriyle zenginleştirir.
     */
    private void enrichListImages(List<BlogDto> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        Map<Long, String> imageMap = jdbcTemplate.query(SQL_SELECT_ALL_IMAGES, resultSet -> {
            Map<Long, String> map = new HashMap<>();
            while (resultSet.next()) {
                map.put(resultSet.getLong("BLOG_ID"), resultSet.getString("IMAGE"));
            }
            return map;
        });

        for (BlogDto dto : list) {
            if (dto == null || dto.getBlogId() == null) {
                continue;
            }

            String databaseImage = imageMap.get(dto.getBlogId());
            if (hasText(databaseImage)) {
                dto.setImage(databaseImage);
            }
        }
    }

    private void enrichResultImage(Object result) {
        if (!(result instanceof BlogDto dto) || dto.getBlogId() == null) {
            return;
        }

        String databaseImage = readImageById(dto.getBlogId());
        if (hasText(databaseImage)) {
            dto.setImage(databaseImage);
        }
    }

    private String readImageById(Long blogId) {
        if (blogId == null) {
            return null;
        }

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_IMAGE_BY_ID, String.class, blogId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    private Long resolveBlogId(Object created, BlogDto requestDto) {
        if (created instanceof BlogDto createdDto && createdDto.getBlogId() != null) {
            return createdDto.getBlogId();
        }

        Long reflectedId = invokeLongGetter(created, "getBlogId");
        if (reflectedId == null) {
            reflectedId = invokeLongGetter(created, "getId");
        }

        if (reflectedId != null) {
            return reflectedId;
        }

        if (requestDto != null && requestDto.getBlogId() != null) {
            return requestDto.getBlogId();
        }

        if (requestDto != null && hasText(requestDto.getHeader()) && hasText(requestDto.getTitle())) {
            try {
                return jdbcTemplate.queryForObject(
                        SQL_FIND_LAST_MATCHING_ID,
                        Long.class,
                        requestDto.getHeader(),
                        requestDto.getTitle()
                );
            } catch (Exception ex) {
                log.warn("Yeni blog ID fallback sorgusuyla alınamadı.", ex);
            }
        }

        return null;
    }

    private Long invokeLongGetter(Object target, String methodName) {
        if (target == null) {
            return null;
        }

        try {
            Method method = target.getClass().getMethod(methodName);
            Object value = method.invoke(target);

            if (value instanceof Number number) {
                return number.longValue();
            }
        } catch (Exception ignored) {
            // Farklı servis dönüş tipleri için sessiz fallback.
        }

        return null;
    }

    /// ///////////////////////////////////////////////////////////////////////////
    /// FILE STORAGE
    /// ///////////////////////////////////////////////////////////////////////////

    private boolean hasFile(MultipartFile multipartFile) {
        return multipartFile != null && !multipartFile.isEmpty();
    }

    /**
     * Görseli file.upload-dir/blog klasörüne kaydeder ve DB'de tutulacak
     * public URL yolunu döndürür: /upload/blog/<unique-name>
     */
    private String storeBlogImage(MultipartFile multipartFile) throws IOException {
        validateImage(multipartFile);

        String originalName = StringUtils.cleanPath(
                multipartFile.getOriginalFilename() == null
                        ? "blog-image"
                        : multipartFile.getOriginalFilename()
        );

        String safeName = originalName
                .replace("..", "")
                .replaceAll("[^a-zA-Z0-9._-]", "_");

        if (safeName.isBlank()) {
            safeName = "blog-image";
        }

        String uniqueName = UUID.randomUUID().toString().replace("-", "") + "_" + safeName;

        Path uploadRoot = fileProps.getUploadRoot().toAbsolutePath().normalize();
        Path blogDirectory = uploadRoot.resolve("blog").normalize();
        Files.createDirectories(blogDirectory);

        Path target = blogDirectory.resolve(uniqueName).normalize();
        if (!target.startsWith(blogDirectory)) {
            throw new IllegalArgumentException("Geçersiz görsel dosya yolu.");
        }

        Files.copy(multipartFile.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        log.info("Blog görseli kaydedildi: {}", target);

        return "/upload/blog/" + uniqueName;
    }

    private void validateImage(MultipartFile multipartFile) {
        if (multipartFile.getSize() > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException("Görsel dosyası en fazla 10 MB olabilir.");
        }

        String contentType = multipartFile.getContentType();
        if (contentType == null || !contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
            throw new IllegalArgumentException("Yalnızca görsel dosyaları yüklenebilir.");
        }

        String originalName = multipartFile.getOriginalFilename();
        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf('.') + 1)
                    .toLowerCase(Locale.ROOT);
        }

        if (!ALLOWED_IMAGE_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(
                    "Desteklenmeyen görsel uzantısı. İzin verilenler: jpg, jpeg, png, gif, webp, bmp, svg"
            );
        }
    }

    private void deleteStoredImageQuietly(String publicImagePath) {
        if (!hasText(publicImagePath)) {
            return;
        }

        String normalized = publicImagePath.replace("\\", "/");
        String prefix = "/upload/";

        if (!normalized.startsWith(prefix)) {
            // Dış URL veya uygulamanın yönetmediği dosya.
            return;
        }

        String relativePart = normalized.substring(prefix.length());

        try {
            Path uploadRoot = fileProps.getUploadRoot().toAbsolutePath().normalize();
            Path filePath = uploadRoot.resolve(relativePart).normalize();

            if (filePath.startsWith(uploadRoot)) {
                Files.deleteIfExists(filePath);
            }
        } catch (Exception ex) {
            log.warn("Eski blog görseli silinemedi: {}", publicImagePath, ex);
        }
    }

    /// ///////////////////////////////////////////////////////////////////////////
    /// GENERAL HELPERS
    /// ///////////////////////////////////////////////////////////////////////////

    private void normalizeDtoImage(BlogDto blogDto) {
        if (blogDto == null) {
            return;
        }

        blogDto.setImage(normalizeImagePath(blogDto.getImage()));
    }

    private String normalizeImagePath(String imagePath) {
        if (!hasText(imagePath)) {
            return null;
        }

        String normalized = imagePath.trim().replace("\\", "/");

        // Eski yapay placeholder değerini gerçek görsel gibi kaydetme.
        if ("resim.png".equalsIgnoreCase(normalized)) {
            return null;
        }

        return normalized;
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private void requireBlogId(Long blogId, String message) {
        if (blogId == null) {
            throw new IllegalStateException(message);
        }
    }

    private String safeMessage(Exception ex) {
        if (ex == null) {
            return "Bilinmeyen hata";
        }

        if (hasText(ex.getMessage())) {
            return ex.getMessage();
        }

        return ex.getClass().getSimpleName();
    }

} // end BlogApiImpl
