package com.hamitmizrak.business.services.impl;

import com.hamitmizrak.business.dto.BlogCategoryDto;
import com.hamitmizrak.business.services.interfaces.IBlogCategoryServices;
import com.hamitmizrak.data.entity.BlogCategoryEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

// Lombok
@Log4j2
@Service
public class BlogCategoryServicesImpl implements IBlogCategoryServices<BlogCategoryDto, BlogCategoryEntity> {

    ///


    /// MAPPER /////////////////////////////////////////////////////////////////
    @Override
    public BlogCategoryDto entityToDto(BlogCategoryEntity blogCategoryEntity) {
        return null;
    }

    @Override
    public BlogCategoryEntity dtoToEntity(BlogCategoryDto blogCategoryDto) {
        return null;
    }

    /// SPEED, DELETE //////////////////////////////////////////////////////////
    @Override
    public List<BlogCategoryDto> speedData(Integer data) {
        return List.of();
    }

    @Override
    public List<BlogCategoryDto> deleteData() {
        return List.of();
    }

    /// CRUD ///////////////////////////////////////////////////////////////////
    // CREATE (BLOG CATEGORY)
    @Override
    public BlogCategoryDto objectServiceCreate(BlogCategoryDto blogCategoryDto) {
        return null;
    }

    // LIST (BLOG CATEGORY)
    @Override
    public List<BlogCategoryDto> objectServiceList() {
        return List.of();
    }

    // FIND BY ID (BLOG CATEGORY)
    @Override
    public BlogCategoryDto objectServiceFindById(Long id) {
        return null;
    }

    // UPDATE (BLOG CATEGORY)
    @Override
    public BlogCategoryDto objectServiceUpdate(Long id, BlogCategoryDto blogCategoryDto) {
        return null;
    }


    // DELETE BY ID (BLOG CATEGORY)
    @Override
    public BlogCategoryDto objectServiceDelete(Long id) {
        return null;
    }

} // end BlogCategoryServicesImpl
