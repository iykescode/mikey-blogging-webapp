package com.iykescode.blog.mikeybloggingwebapp.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO extends BaseEntityDTO {

    private Integer id;

    private String name;
}
