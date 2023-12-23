package com.iykescode.blog.mikeybloggingwebapp.dto;

import lombok.*;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonProfileDTO extends BaseEntityDTO {

    private Integer id;

    private String headline;

    private String summary;

    @URL(message = "This must be a valid link starting with 'http://' or 'https://'")
    private String facebook;

    @URL(message = "This must be a valid link starting with 'http://' or 'https://'")
    private String twitter;

    @URL(message = "This must be a valid link starting with 'http://' or 'https://'")
    private String instagram;

    @URL(message = "This must be a valid link starting with 'http://' or 'https://'")
    private String linkedIn;
}
