package com.hamitmizrak.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamitmizrak.business.dto.BlogCategoryDto;
import com.hamitmizrak.business.services.impl.BlogServicesImpl;
import com.hamitmizrak.controller.interfaces.IBlogApi;
import com.hamitmizrak.error.ApiResult;
import com.hamitmizrak.utily.FrontEnd;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
public class BlogApiImpl  implements IBlogApi<BlogCategoryDto> {


    // Field
    private final BlogServicesImpl blogServicesImpl;
    private final ObjectMapper objectMapper;

    /// /////////////////////////////////////
    /// SPEED DATA
    // http://localhost:4444/blog/category/api/v1.0.0/speed
    @Override
    public ResponseEntity<ApiResult<List<BlogCategoryDto>>> speedData(Integer data) {
        return null;
    }

    /// DELETE ALL
    @Override
    public ResponseEntity<ApiResult<List<BlogCategoryDto>>> deleteData() {
        return null;
    }

    /// /////////////////////////////////////
    /// CREATE (RESİMSİZ)
    // http://localhost:4444/blog/category/api/v1.0.0/create
    @Override
    @PostMapping("/create")
    public ResponseEntity<ApiResult<?>> objectServiceCreate(@Valid @RequestBody BlogCategoryDto blogCategoryDto) {
        return null;
    }

    /// CREATE (RESİMLİ)
    // http://localhost:4444/blog/category/api/v1.0.0/create
    @Override
    public ResponseEntity<ApiResult<?>> objectServiceCreateWithFile(@Valid @RequestBody BlogCategoryDto blogCategoryDto, MultipartFile multipartFile) {
        return null;
    }

    // LIST
    @Override
    @GetMapping("/list")
    public ResponseEntity<ApiResult<List<BlogCategoryDto>>> objectServiceList() {
        return null;
    }

    // FIND
    @Override
    @GetMapping("/find/{id}")
    public ResponseEntity<ApiResult<?>> objectServiceFindById(@PathVariable(name = "id") Long id) {
        return null;
    }

    // UPDATE (RESİMSİZ)
    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResult<?>> objectServiceUpdate(@PathVariable(name = "id") Long id, @Valid @RequestBody BlogCategoryDto blogCategoryDto) {
        return null;
    }

    // UPDATE (RESİMLİ)
    @Override
    public ResponseEntity<ApiResult<?>> objectServiceUpdateWithFile(@PathVariable(name = "id") Long id, @Valid @RequestBody BlogCategoryDto blogCategoryDto, MultipartFile multipartFile) {
        return null;
    }

    // DELETE
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResult<?>> objectServiceDelete(Long id) {
        return null;
    }

} //end  BlogApiImpl
