package com.hamitmizrak.business.dto;


import com.hamitmizrak.audit.AuditingAwareBaseDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogCategoryDto extends AuditingAwareBaseDto implements Serializable {

    // Serial
    private static final long serialVersionUID = 1L;

    // Field
    private String categoryId;

    @NotEmpty(message = "{blog.category.least.validation.constraints.NotNull.message}")
    @Size(min = 3, message = "{blog.category.unique.validation.constraints.NotNull.message}")
    private String categoryName;
    private Date systemCreatedDate;
}
