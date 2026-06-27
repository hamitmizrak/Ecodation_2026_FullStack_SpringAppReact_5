package com.hamitmizrak.business.dto;


import com.hamitmizrak.audit.AuditingAwareBaseDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.io.Serial;
import java.io.Serializable;

// Lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Log4j2

//  BlogDto(N) -BlogCategory(1)
public class BlogDto  extends AuditingAwareBaseDto implements Serializable {

    // Serial
    @Serial
    private static final long serialVersionUID = 1L;

    // Field
    private Long blogId;

    // Header
    @NotEmpty(message = "{blog.header.least.validation.constraints.NotNull.message}")
    @Size(min = 3, message = "{blog.header.least.validation.constraints.NotNull.message}")
    private String header;

    // Title
    @NotEmpty(message = "{blog.title.least.validation.constraints.NotNull.message}")
    private String title;

    // Content
    @NotEmpty(message = "{blog.content.least.validation.constraints.NotNull.message}")
    private String content;

    // Picture
    @Builder.Default
    private String image="resim.png";

    // Relation
    private BlogCategoryDto blogCategoryDto;
}
