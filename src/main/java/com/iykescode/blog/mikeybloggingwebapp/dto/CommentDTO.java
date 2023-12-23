package com.iykescode.blog.mikeybloggingwebapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO extends BaseEntityDTO {

    private Integer id;

    @NotBlank(message = "This field can't be blank !!")
    private String content;

    private PersonDTO person;

    private PostDTO post;
}