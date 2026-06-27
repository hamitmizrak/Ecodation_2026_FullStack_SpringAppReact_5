package com.hamitmizrak.data.mapper;

import com.hamitmizrak.business.dto.BlogCategoryDto;
import com.hamitmizrak.data.entity.BlogCategoryEntity;
import lombok.experimental.UtilityClass;

// Lombok
@UtilityClass
public class BlogCategoryMapper {

    //toDto
    public BlogCategoryDto toDto (BlogCategoryEntity blogCategoryEntity){
        if(blogCategoryEntity == null){
            return null;
        }
        return BlogCategoryDto.builder()
                .categoryId(blogCategoryEntity.getBlogCategoryId())
                .categoryName(blogCategoryEntity.getCategoryName())
                .build();
    }

    // toEntity
    public BlogCategoryEntity toEntity (BlogCategoryDto blogCategoryDto){
        if(blogCategoryDto == null){
            return null;
        }
        return BlogCategoryEntity.builder()
                .blogCategoryId(blogCategoryDto.getCategoryId())
                .categoryName(blogCategoryDto.getCategoryName())
                .build();
    }

}
