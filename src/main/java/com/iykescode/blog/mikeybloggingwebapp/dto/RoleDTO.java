package com.iykescode.blog.mikeybloggingwebapp.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO extends BaseEntityDTO {

    private Integer id;

    private String roleName;
}
