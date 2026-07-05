package com.hamitmizrak.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamitmizrak.business.dto.BlogDto;
import com.hamitmizrak.business.services.impl.BlogServicesImpl;
import com.hamitmizrak.business.services.interfaces.IBlogServices;
import com.hamitmizrak.controller.interfaces.IBlogApi;
import com.hamitmizrak.error.ApiResult;
import com.hamitmizrak.utily.FrontEnd;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// Lombok
@RequiredArgsConstructor
@Log4j2

// Api
@RestController
@RequestMapping("/blog/api/v1.0.0")
@CrossOrigin(origins = FrontEnd.REACT_URL)
public class BlogApiImpl  implements IBlogApi<BlogDto> {

    // Field
    private final BlogServicesImpl iblogservices;
    private final ObjectMapper objectMapper;
    private final IBlogServices iBlogServices;

    /// /////////////////////////////////////
    /// SPEED DATA
    // http://localhost:4444/blog/api/v1.0.0/speed
    @Override
    @GetMapping("speed")
    public ResponseEntity<ApiResult<List<BlogDto>>> speedData(Integer data) {
        return null;
    }

    /// DELETE ALL
    // http://localhost:4444/blog/api/v1.0.0/delete-all
    @Override
    @GetMapping("delete-all")
    public ResponseEntity<ApiResult<List<BlogDto>>> deleteData() {
        return null;
    }

    /// /////////////////////////////////////
    /// CREATE (RESİMSİZ) JSON
    // http://localhost:4444/blog/category/api/v1.0.0/create
    @Override
    @PostMapping(value = "/create",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<?>> objectServiceCreate(@Valid @RequestBody BlogDto blogDto) {
        try {
            return ResponseEntity.ok(ApiResult.success(iblogservices.objectServiceCreate(blogDto)));
        } catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("serverError", ex.getMessage(), "/blog/api/v1.0.0/create"));
        }
    }

    /// CREATE (RESİMLİ) Multipart
    // http://localhost:4444/blog/category/api/v1.0.0/create
    @Override
    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<?>> objectServiceCreateWithFile(
            @RequestPart("blog") String json,
            @RequestPart(value="file",required = false) MultipartFile multipartFile)  {
        try {
            BlogDto blogDto = objectMapper.readValue(json,BlogDto.class);
            return ResponseEntity.ok(ApiResult.success(iblogservices.objectServiceCreateWithFile(blogDto,multipartFile)));
        } catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("serverError", ex.getMessage(), "/blog/api/v1.0.0/create"));
        }
    }

    // LIST
    @Override
    @GetMapping("/list")
    public ResponseEntity<ApiResult<List<BlogDto>>> objectServiceList() {
        try {
            List<BlogDto> list = iblogservices.objectServiceList();
            return ResponseEntity.ok(ApiResult.success(list));
        } catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("serverError", ex.getMessage(), "/blog/api/v1.0.0/list"));
        }
    }

    // FIND
    @Override
    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResult<?>> objectServiceFindById(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok(ApiResult.success(iblogservices.objectServiceFindById(id)));
        } catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("serverError", ex.getMessage(), "/blog/api/v1.0.0/find/"+id));
        }
    }

    // UPDATE (RESİMSİZ) JSON
    @Override
    @PutMapping(value="/update/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResult<?>> objectServiceUpdate(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody BlogDto blogDto) {
        try {
            return ResponseEntity.ok(ApiResult.success(iblogservices.objectServiceCreate(blogDto)));
        } catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("serverError", ex.getMessage(), "/blog/api/v1.0.0/create"));
        }
    }

    // UPDATE (RESİMLİ) MULTIPART
    @Override
    @PutMapping(value="/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResult<?>> objectServiceUpdateWithFile(
            @PathVariable(name = "id") Long id,
            @RequestPart("blog") String json,
      @RequestPart(value="file",required = false) MultipartFile multipartFile
    ) {
        try {
            BlogDto blogDto = objectMapper.readValue(json,BlogDto.class);
            return ResponseEntity.ok(ApiResult.success(iblogservices.objectServiceUpdateWithFile(id,blogDto,multipartFile)));
        } catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("serverError", ex.getMessage(), "/blog/api/v1.0.0/create"));
        }
    }

    // DELETE
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResult<?>> objectServiceDelete(@PathVariable("id") Long id) {
        try {
            BlogDto deleteBlogDto = (BlogDto) iBlogServices.objectServiceDelete(id);
            return ResponseEntity.ok(ApiResult.success(deleteBlogDto));
        } catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("serverError", ex.getMessage(), "/blog/category/api/v1.0.0/delete/"+id));
        }
    }

} //end  BlogApiImpl
