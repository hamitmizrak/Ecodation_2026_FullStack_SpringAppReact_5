package com.hamitmizrak.controller;

import com.hamitmizrak.error.ApiResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

// D: Dto
public interface IImageApi<D>{

    // Resim ekleme
    public ResponseEntity<ApiResult<?>> objectServiceCreateWithFile(String json, MultipartFile multipartFile);
    public ResponseEntity<ApiResult<?>> objectServiceUpdateWithFile(Long id,String json, MultipartFile multipartFile);
}
