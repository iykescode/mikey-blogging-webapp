package com.iykescode.blog.mikeybloggingwebapp.model;

import com.iykescode.blog.mikeybloggingwebapp.annotation.ImageSizeValidator;
import com.iykescode.blog.mikeybloggingwebapp.annotation.ImageValidator;
import com.iykescode.blog.mikeybloggingwebapp.constants.ImageDIRConstant;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_images")
public class PersonImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    @Column(name = "id")
    private Integer id;

    @Column(name = "image")
    private String image;

    @ImageValidator
    @ImageSizeValidator(message = "Image size must not be more than 5MB")
    @Transient
    private MultipartFile imageContent;

    @PreRemove
    private void preRemove() {
        // Delete the associated file when the post is removed
        if (image != null) {
            File file = new File(ImageDIRConstant.userImages + image);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
