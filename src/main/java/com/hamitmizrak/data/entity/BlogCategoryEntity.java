package com.hamitmizrak.data.entity;


import com.hamitmizrak.audit.AuditingAwareBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.util.List;

// Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2

// Table
@Entity
@Table(name="blog_categories")

// BlogCategory(1) - BlogDto(N)
public class BlogCategoryEntity extends AuditingAwareBaseEntity {

    // Field

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_Category_Id")
    private Long blogCategoryId;

    // categoryName
    @Column(unique = true,nullable = false,length = 200)
    private String categoryName;


    // Relation
    @OneToMany(mappedBy = "blogCategoryEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BlogEntity> blogEntityList;
}
