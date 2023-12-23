package com.iykescode.blog.mikeybloggingwebapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_profiles")
public class PersonProfile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    @Column(name = "id")
    private Integer id;

    @Column(name = "headline")
    private String headline;

    @Column(name = "summary")
    private String summary;

    @URL(message = "This must be a valid link starting with 'http://' or 'https://'")
    @Column(name = "facebook")
    private String facebook;

    @URL(message = "This must be a valid link starting with 'http://' or 'https://'")
    @Column(name = "twitter")
    private String twitter;

    @URL(message = "This must be a valid link starting with 'http://' or 'https://'")
    @Column(name = "instagram")
    private String instagram;

    @URL(message = "This must be a valid link starting with 'http://' or 'https://'")
    @Column(name = "linked_in")
    private String linkedIn;
}
