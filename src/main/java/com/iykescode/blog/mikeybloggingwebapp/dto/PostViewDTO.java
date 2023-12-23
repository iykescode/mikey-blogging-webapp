package com.iykescode.blog.mikeybloggingwebapp.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostViewDTO extends BaseEntityDTO {

    private Integer id;

    private PersonDTO person;

    private PostDTO post;
}
