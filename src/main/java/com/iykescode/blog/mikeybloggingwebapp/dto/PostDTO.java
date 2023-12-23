package com.iykescode.blog.mikeybloggingwebapp.dto;

import com.iykescode.blog.mikeybloggingwebapp.validation.BasicPostFieldValidation;
import com.iykescode.blog.mikeybloggingwebapp.validation.StatusMessageDetailsFieldValidation;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO extends BaseEntityDTO {

    private Integer id;

    @Size(message = "This field must be 5 characters or more !!",
            min = 5, groups = { BasicPostFieldValidation.class })
    private String title;

    private String slug;

    private String quote;

    @Size(message = "This field must be 5 characters or more !!",
            min = 5, groups = { BasicPostFieldValidation.class })
    private String content;

    private String tags;

    @Transient
    private String newTag;

    private String status;

    private String statusMessage;

    @Size(message = "This field must be 5 characters or more !!",
            min = 5, groups = { StatusMessageDetailsFieldValidation.class })
    private String statusMessageDetails;

    private CategoryDTO category;

    @NotNull(message = "This field can't be empty !!",
            groups = { BasicPostFieldValidation.class })
    private Integer formCategory;

    private PersonDTO person;

    private PostImageDTO postImage;
}
