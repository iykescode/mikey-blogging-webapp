package com.iykescode.blog.mikeybloggingwebapp.dto;

import com.iykescode.blog.mikeybloggingwebapp.annotation.UniqueCategoryValidator;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO extends BaseEntityDTO {

    private Integer id;

    @UniqueCategoryValidator
    @NotBlank(message = "This field can't be blank !!")
    private String name;
}
