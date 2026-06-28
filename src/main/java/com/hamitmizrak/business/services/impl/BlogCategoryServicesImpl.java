package com.hamitmizrak.business.services.impl;

import com.hamitmizrak.bean.ModelMapperBean;
import com.hamitmizrak.business.dto.BlogCategoryDto;
import com.hamitmizrak.business.services.interfaces.IBlogCategoryServices;
import com.hamitmizrak.data.entity.BlogCategoryEntity;
import com.hamitmizrak.data.mapper.BlogCategoryMapper;
import com.hamitmizrak.data.repository.IBlogCategoryRepository;
import com.hamitmizrak.exception._404_NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    @Transactional
    public List<BlogCategoryDto> speedData(Integer data) {

        List<BlogCategoryDto> listData= new ArrayList<>();

        if(data!=null){
            for (int i = 1; i <=data ; i++) {
                BlogCategoryEntity blogCategoryEntity =new BlogCategoryEntity();
                blogCategoryEntity.setCategoryName(UUID.randomUUID().toString());
                iBlogCategoryRepository.save(blogCategoryEntity);
                listData.add(BlogCategoryMapper.toDto(blogCategoryEntity));
            }
        }else {
            throw new NullPointerException("Integer null");
        }
        return listData;
    }

    @Override
    @Transactional
    public List<BlogCategoryDto> deleteData() {
        iBlogCategoryRepository.deleteAll();
        return List.of();
    }

    /////////////////////////////////////////////////////////////////////////////
    /// CRUD ///////////////////////////////////////////////////////////////////
    // CREATE (BLOG CATEGORY)
    @Override
    @Transactional
    public BlogCategoryDto objectServiceCreate(BlogCategoryDto blogCategoryDto) {
        if(blogCategoryDto==null || blogCategoryDto.getCategoryName()==null || blogCategoryDto.getCategoryName().isBlank()){
            throw new NullPointerException("Blog Kategori adı zorunludur");
        }

        if (iBlogCategoryRepository.existsByCategoryNameIgnoreCase(blogCategoryDto.getCategoryName()))
         {
             throw new NullPointerException("Blog Kategori adı bulunmaktadır");
        }

        BlogCategoryEntity saved =iBlogCategoryRepository.save(dtoToEntity(blogCategoryDto) );
        return entityToDto(saved);
    }

    // LIST (BLOG CATEGORY)
    @Override
    @Transactional(readOnly = true)
    public List<BlogCategoryDto> objectServiceList() {
        return iBlogCategoryRepository.findAll().stream().map(this::entityToDto).toList();
    }

    // FIND BY ID (BLOG CATEGORY)
    @Override
    @Transactional(readOnly = true)
    public BlogCategoryDto objectServiceFindById(Long id) {
        BlogCategoryEntity blogCategoryEntity = iBlogCategoryRepository.findById(id)
                .orElseThrow(()-> new _404_NotFoundException(id+" id'li blog kategori bulunamadı"));
        return entityToDto(blogCategoryEntity);
    }

    // UPDATE (BLOG CATEGORY)
    @Override
    @Transactional
    public BlogCategoryDto objectServiceUpdate(Long id, BlogCategoryDto blogCategoryDto) {
        return null;
    }


    // DELETE BY ID (BLOG CATEGORY)
    @Override
    @Transactional
    public BlogCategoryDto objectServiceDelete(Long id) {
        return null;
    }

} // end BlogCategoryServicesImpl
