package com.hamitmizrak.business.services.impl;

import com.hamitmizrak.bean.ModelMapperBean;
import com.hamitmizrak.business.dto.BlogDto;
import com.hamitmizrak.business.services.interfaces.IBlogServices;
import com.hamitmizrak.data.entity.BlogCategoryEntity;
import com.hamitmizrak.data.entity.BlogEntity;
import com.hamitmizrak.data.mapper.BlogMapper;
import com.hamitmizrak.data.repository.IBlogCategoryRepository;
import com.hamitmizrak.data.repository.IBlogRepository;
import com.hamitmizrak.exception.HamitMizrakException;
import com.hamitmizrak.exception._404_NotFoundException;
import com.hamitmizrak.file_upload.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// Lombok
@RequiredArgsConstructor //DI
@Log4j2
@Service
public class BlogServicesImpl implements IBlogServices<BlogDto, BlogEntity> {

    /// CDI
    private final IBlogRepository iBlogRepository;
    private final IBlogCategoryRepository iBlogCategoryRepository;
    private final ModelMapperBean modelMapperBean;
    private final ImageService imageService;

    /// MAPPER /////////////////////////////////////////////////////////////////
    @Override
    public BlogDto entityToDto(BlogEntity blogEntity) {
        // 1.YOL
        //return modelMapperBean.modelMapperMethod().map(BlogEntity, BlogDto.class);

        // 2.YOL
        return BlogMapper.toDto(blogEntity);
    }

    @Override
    public BlogEntity dtoToEntity(BlogDto blogDto) {
        return BlogMapper.toEntity(blogDto);
    }

    /////////////////////////////////////////////////////////////////////////////
    /// SPEED, DELETE //////////////////////////////////////////////////////////
    @Override
    @Transactional
    public List<BlogDto> speedData(Integer data) {
        return null;
    }

    @Override
    @Transactional
    public List<BlogDto> deleteData() {
        iBlogRepository.deleteAll();
        return List.of();
    }

    /////////////////////////////////////////////////////////////////////////////
    /// CRUD ///////////////////////////////////////////////////////////////////
    // CREATE RESİMSİZ (BLOG)
    @Override
    @Transactional
    public BlogDto objectServiceCreate(BlogDto blogDto) {
        validate(blogDto,true);

        // Blog'tan öncesinde Kategorisine bakmak
        Long catId= blogDto.getBlogCategoryDto()!=null ? blogDto.getBlogCategoryDto().getCategoryId():null;
        if(catId==null){
            throw new HamitMizrakException("Kategori seçiniz");
        }

        // Blog category'i bul
        BlogCategoryEntity blogCategoryEntity = iBlogCategoryRepository.findById(catId)
                .orElseThrow(()-> new _404_NotFoundException(catId+ " id'li kategori bulunmadı"));

        // BlogEntity çağır ve category eşle
        BlogEntity blogEntity = dtoToEntity(blogDto);
        blogEntity.setBlogCategoryEntity(blogCategoryEntity);

        // Repository  save
        BlogEntity createdEntity = iBlogRepository.save(blogEntity);
        return entityToDto(createdEntity);
    }

    // CREATE RESİMLi (BLOG)
    @Override
    @Transactional
    public BlogDto objectServiceCreateWithFile(BlogDto blogDto, MultipartFile multipartFile) {
        if(multipartFile!=null && !multipartFile.isEmpty()){
            String relative = imageService.saveBlogImage(multipartFile);
            blogDto.setImage(relative);
        }
        return objectServiceCreate(blogDto);
    }


    // LIST (BLOG)
    @Override
    @Transactional(readOnly = true)
    public List<BlogDto> objectServiceList() {
        return iBlogRepository.findAll().stream().map(this::entityToDto).toList();
    }

    // FIND BY ID (BLOG)
    @Override
    @Transactional(readOnly = true)
    public BlogDto objectServiceFindById(Long id) {
        BlogEntity blogEntity =iBlogRepository.findById(id).orElseThrow(()-> new _404_NotFoundException(id+ " id'li blog kategori bulunamadı"));
        return entityToDto(blogEntity);
    }

    // UPDATE RESİMSİZ (BLOG)
    @Override
    @Transactional
    public BlogDto objectServiceUpdate(Long id, BlogDto blogDto) {
        validate(blogDto,true);

        BlogEntity blogEntity = iBlogRepository.findById(id).orElseThrow(()->new HamitMizrakException(id+" id'li blog bulunamadı"));

        if(blogDto.getBlogCategoryDto()!=null && blogDto.getBlogCategoryDto().getCategoryId()!=null){
            Long catId = blogDto.getBlogCategoryDto().getCategoryId();
            BlogCategoryEntity blogCategoryEntity = iBlogCategoryRepository.findById(catId).orElseThrow(()-> new _404_NotFoundException(id+ " id'li kategori bulunamadı"));

            blogEntity.setBlogCategoryEntity(blogCategoryEntity);
        }

        return entityToDto(blogEntity);
    }

    // UPDATE RESİMSİZ (BLOG)
    @Override
    @Transactional
    public BlogDto objectServiceUpdateWithFile(Long id, BlogDto blogDto, MultipartFile multipartFile) {
        BlogEntity current =iBlogRepository.findById(id).orElseThrow(()-> new _404_NotFoundException(id+ " id'li blog bulunamadı"));

        // Güncellenecek resimde eski resimi silelim yoksa server şişer
        String oldUrl =  current.getImage();

        if(multipartFile!=null && !multipartFile.isEmpty()){
            String relative = imageService.saveBlogImage(multipartFile);
            blogDto.setImage(relative);
        }
        BlogDto updated = objectServiceUpdate(id, blogDto);

        //
        if(multipartFile!=null && !multipartFile.isEmpty() && oldUrl!=null && oldUrl.startsWith("/upload/") && !oldUrl.equals(updated.getImage())){
            try {
                imageService.deleteByUrl(oldUrl);
            }catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
        return updated;
    }


    // DELETE BY ID (BLOG)
    @Override
    @Transactional
    public BlogDto objectServiceDelete(Long id) {
        BlogEntity findDelete = dtoToEntity(objectServiceFindById(id));

        String img = findDelete.getImage();
        if(img!=null && img.startsWith("/upload/")){
            try {
                imageService.deleteByUrl(img);
            } catch (Exception e) {
                //throw new RuntimeException(e);
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }

        iBlogRepository.deleteById(id);
        return entityToDto(findDelete);
    }

    /// //////////////////////////////////////////////////////////////
    private void validate(BlogDto blogDto, boolean isResult) {
        if(blogDto==null){
            throw new HamitMizrakException("Blog verisi boş");
        }

        if(isResult){
            if(blogDto.getHeader()==null || blogDto.getHeader().isBlank()){
                throw new HamitMizrakException("Blog başlığı zorunlu");
            }
            if(blogDto.getTitle()==null || blogDto.getTitle().isBlank()){
                throw new HamitMizrakException("Blog alt başlık zorunlu");
            }
            if(blogDto.getContent()==null || blogDto.getContent().isBlank()){
                throw new HamitMizrakException("Blog içeriği zorunlu");
            }

        }
    } // end validate


    private void validateImage(BlogDto blogDto, boolean isResult) {
        if(blogDto==null){
            throw new HamitMizrakException("Blog verisi boş");
        }

        if(isResult){
            if(blogDto.getHeader()==null || blogDto.getHeader().isBlank()){
                throw new HamitMizrakException("Blog başlığı zorunlu");
            }
            if(blogDto.getTitle()==null || blogDto.getTitle().isBlank()){
                throw new HamitMizrakException("Blog alt başlık zorunlu");
            }
            if(blogDto.getContent()==null || blogDto.getContent().isBlank()){
                throw new HamitMizrakException("Blog içeriği zorunlu");
            }

            if(blogDto.getImage()==null || blogDto.getImage().isBlank()){
                throw new HamitMizrakException("Blog Resim zorunlu");
            }

        }
    } // end validate



} // end BlogCategoryServicesImpl
