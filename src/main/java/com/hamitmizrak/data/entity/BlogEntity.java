package com.hamitmizrak.data.entity;

import com.hamitmizrak.audit.AuditingAwareBaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;


// Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2

// Table
@Entity
@Table(name="blogs")

// BlogDto(N) - BlogCategory(1)
public class BlogEntity extends AuditingAwareBaseEntity {

    // Field
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long blogId;

    // Header
    @Column(nullable = false, unique = true)
    private String header;

    // Title
    private String title;

    // Content
    @Lob
    private String content;

    // Picture
    private String image;

    // Relation
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="blog_Category_Id",nullable = false)
    private BlogCategoryEntity blogCategoryEntity;
}
