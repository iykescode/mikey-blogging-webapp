package com.iykescode.blog.mikeybloggingwebapp.model;

import com.iykescode.blog.mikeybloggingwebapp.annotation.UniqueCategoryValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Category extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    @Column(name = "id")
    private Integer id;

    @UniqueCategoryValidator
    @NotBlank(message = "This field can't be blank !!")
    @Column(name = "name")
    private String name;
}
