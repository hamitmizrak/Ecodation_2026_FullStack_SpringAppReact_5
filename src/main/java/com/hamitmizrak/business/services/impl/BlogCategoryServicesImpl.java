package com.hamitmizrak.business.services.impl;

import com.hamitmizrak.bean.ModelMapperBean;
import com.hamitmizrak.business.dto.BlogCategoryDto;
import com.hamitmizrak.business.services.interfaces.IBlogCategoryServices;
import com.hamitmizrak.data.entity.BlogCategoryEntity;
import com.hamitmizrak.data.mapper.BlogCategoryMapper;
import com.hamitmizrak.data.repository.IBlogCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Lombok
@RequiredArgsConstructor //DI
@Log4j2
@Service
public class BlogCategoryServicesImpl implements IBlogCategoryServices<BlogCategoryDto, BlogCategoryEntity> {

    /// CDI
    // 1.YOL
    /*
    @Autowired
    private IBlogCategoryRepository iBlogCategoryRepository;
     */

    // 2.YOL
    /*
    private final IBlogCategoryRepository iBlogCategoryRepository;
    @Autowired
    public BlogCategoryServicesImpl(IBlogCategoryRepository iBlogCategoryRepository) {
        this.iBlogCategoryRepository = iBlogCategoryRepository;
    }
    */

    // 3.YOL
    private final IBlogCategoryRepository iBlogCategoryRepository;
    private final ModelMapperBean modelMapperBean;

    /// MAPPER /////////////////////////////////////////////////////////////////
    @Override
    public BlogCategoryDto entityToDto(BlogCategoryEntity blogCategoryEntity) {
        // 1.YOL
        //return modelMapperBean.modelMapperMethod().map(blogCategoryEntity, BlogCategoryDto.class);

        // 2.YOL
        return BlogCategoryMapper.toDto(blogCategoryEntity);
    }

    @Override
    public BlogCategoryEntity dtoToEntity(BlogCategoryDto blogCategoryDto) {
        return BlogCategoryMapper.toEntity(blogCategoryDto);
    }

    /////////////////////////////////////////////////////////////////////////////
    /// SPEED, DELETE //////////////////////////////////////////////////////////
    @Override
    public List<BlogCategoryDto> speedData(Integer data) {
        return List.of();
    }

    @Override
    public List<BlogCategoryDto> deleteData() {
        return List.of();
    }

    /////////////////////////////////////////////////////////////////////////////
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
