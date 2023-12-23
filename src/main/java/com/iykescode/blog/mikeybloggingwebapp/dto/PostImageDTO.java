package com.iykescode.blog.mikeybloggingwebapp.dto;

import com.iykescode.blog.mikeybloggingwebapp.annotation.ImageSizeValidator;
import com.iykescode.blog.mikeybloggingwebapp.annotation.ImageValidator;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostImageDTO extends BaseEntityDTO {

    private Integer id;

    private String image;

    @ImageValidator
    @ImageSizeValidator(message = "Image size must not be more than 5MB")
    private MultipartFile imageContent;
}
