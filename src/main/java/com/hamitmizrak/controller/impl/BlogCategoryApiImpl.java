package com.hamitmizrak.controller.impl;

import com.hamitmizrak.business.dto.BlogCategoryDto;
import com.hamitmizrak.business.services.interfaces.IBlogCategoryServices;
import com.hamitmizrak.controller.interfaces.IBlogCategoryApi;
import com.hamitmizrak.data.entity.BlogCategoryEntity;
import com.hamitmizrak.error.ApiResult;
import com.hamitmizrak.utily.FrontEnd;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Lombok
@RequiredArgsConstructor
@Log4j2

// Api
@RestController
@RequestMapping("/blog/category/api/v1.0.0")
@CrossOrigin(origins = FrontEnd.REACT_URL)
public class BlogCategoryApiImpl implements IBlogCategoryApi<BlogCategoryDto> {

    // Field
    private final IBlogCategoryServices<BlogCategoryDto, BlogCategoryEntity> iBlogCategoryServices;

    /// /////////////////////////////////////
    @Override
    public ResponseEntity<ApiResult<List<BlogCategoryDto>>> speedData(Integer data) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResult<List<BlogCategoryDto>>> deleteData() {
        return null;
    }

    /// /////////////////////////////////////
    /// CREATE
    // http://localhost:4444/blog/category/api/v1.0.0/create
    @Override
    @PostMapping("/create")
    public ResponseEntity<ApiResult<?>> objectServiceCreate(@Valid @RequestBody BlogCategoryDto blogCategoryDto) {
        try {
            return ResponseEntity.ok(ApiResult.success(iBlogCategoryServices.objectServiceCreate(blogCategoryDto)));
        } catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("serverError", ex.getMessage(), "/blog/category/api/v1.0.0/create"));
        }
    }

    // LIST
    @GetMapping("/list")
    @Override
    public ResponseEntity<ApiResult<List<BlogCategoryDto>>> objectServiceList() {
        try {
            List<BlogCategoryDto> list = iBlogCategoryServices.objectServiceList();
            return ResponseEntity.ok(ApiResult.success(list));
        } catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("serverError", ex.getMessage(), "/blog/category/api/v1.0.0/list"));
        }
    }

    // FIND BY ID
    @GetMapping("/find/{id}")
    @Override
    public ResponseEntity<ApiResult<?>> objectServiceFindById(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok(ApiResult.success(iBlogCategoryServices.objectServiceFindById(id)));
        } catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("serverError", ex.getMessage(), "/blog/category/api/v1.0.0/find/"+id));
        }
    }

    // UPDATE
    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<ApiResult<?>> objectServiceUpdate(@PathVariable(name = "id") Long id, @Valid @RequestBody BlogCategoryDto blogCategoryDto) {
        try {

        } catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("serverError", ex.getMessage(), "/blog/category/api/v1.0.0/create"));
        }
        return null;
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<ApiResult<?>> objectServiceDelete(@PathVariable(name = "id") Long id) {
        try {

        } catch (Exception ex) {
            return ResponseEntity.ok(ApiResult.error("serverError", ex.getMessage(), "/blog/category/api/v1.0.0/create"));
        }
        return null;
    }


} // end BlogCategoryApiImpl
