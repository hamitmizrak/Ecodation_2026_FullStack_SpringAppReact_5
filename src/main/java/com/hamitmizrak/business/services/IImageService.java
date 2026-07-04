package com.hamitmizrak.business.services;

import org.springframework.web.multipart.MultipartFile;

// D: Dto
// E: Entity
public interface IImageService <D>{

    // Resim ekleme
    public D objectServiceCreateWithFile(D d, MultipartFile multipartFile);
    public D objectServiceUpdateWithFile(Long id,D d, MultipartFile multipartFile);
}
