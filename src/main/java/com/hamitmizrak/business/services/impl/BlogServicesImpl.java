package com.hamitmizrak.business.services.impl;

import com.hamitmizrak.bean.ModelMapperBean;
import com.hamitmizrak.business.dto.BlogDto;
import com.hamitmizrak.business.services.interfaces.IBlogServices;
import com.hamitmizrak.data.entity.BlogEntity;
import com.hamitmizrak.data.entity.BlogEntity;
import com.hamitmizrak.data.mapper.BlogCategoryMapper;
import com.hamitmizrak.data.mapper.BlogMapper;
import com.hamitmizrak.data.repository.IBlogRepository;
import com.hamitmizrak.exception._404_NotFoundException;
import com.hamitmizrak.file_upload.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Lombok
@RequiredArgsConstructor //DI
@Log4j2
@Service
public class BlogServicesImpl implements IBlogServices<BlogDto, BlogEntity> {

    /// CDI
    private final IBlogRepository iBlogRepository;
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
    // CREATE (BLOG CATEGORY)
    @Override
    @Transactional
    public BlogDto objectServiceCreate(BlogDto blogDto) {
        return null;
    }

    // LIST (BLOG CATEGORY)
    @Override
    @Transactional(readOnly = true)
    public List<BlogDto> objectServiceList() {
        return null;
    }

    // FIND BY ID (BLOG CATEGORY)
    @Override
    @Transactional(readOnly = true)
    public BlogDto objectServiceFindById(Long id) {
        return null;
    }

    // UPDATE (BLOG CATEGORY)
    @Override
    @Transactional
    public BlogDto objectServiceUpdate(Long id, BlogDto blogDto) {
        return null;
    }


    // DELETE BY ID (BLOG CATEGORY)
    @Override
    @Transactional
    public BlogDto objectServiceDelete(Long id) {
        BlogEntity findDelete = dtoToEntity(objectServiceFindById(id));
        iBlogRepository.deleteById(id);
        return entityToDto(findDelete);
    }

} // end BlogCategoryServicesImpl
