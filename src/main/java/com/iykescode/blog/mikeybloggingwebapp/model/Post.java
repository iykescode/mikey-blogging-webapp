package com.iykescode.blog.mikeybloggingwebapp.model;

import com.iykescode.blog.mikeybloggingwebapp.validation.BasicPostFieldValidation;
import com.iykescode.blog.mikeybloggingwebapp.validation.StatusMessageDetailsFieldValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    @Column(name = "id")
    private Integer id;

    @Size(message = "This field must be 5 characters or more !!",
            min = 5, groups = { BasicPostFieldValidation.class })
    @Column(name = "title")
    private String title;

    @Column(name = "slug")
    private String slug;

    @Column(name = "quote")
    private String quote;

    @Size(message = "This field must be 5 characters or more !!",
            min = 5, groups = { BasicPostFieldValidation.class })
    @Column(name = "content")
    private String content;

    @Column(name = "tags")
    private String tags;

    @Transient
    private String newTag;

    @Column(name = "status")
    private String status;

    @Column(name = "status_message")
    private String statusMessage;

    @Size(message = "This field must be 5 characters or more !!",
            min = 5, groups = { StatusMessageDetailsFieldValidation.class })
    @Column(name = "status_message_details")
    private String statusMessageDetails;

    @NotNull(message = "This field can't be empty !!",
            groups = { BasicPostFieldValidation.class })
    @ManyToOne(cascade = CascadeType.PERSIST, optional = false, targetEntity = Category.class)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @NotNull(message = "This field can't be empty !!",
            groups = { BasicPostFieldValidation.class })
    @Transient
    private Integer formCategory;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Person.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Person person;

    @ManyToOne(cascade = CascadeType.REMOVE, targetEntity = PostImage.class)
    @JoinColumn(name = "post_image_id", referencedColumnName = "id")
    private PostImage postImage;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, targetEntity = PostView.class)
    private List<PostView> postViews;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, targetEntity = Comment.class)
    private List<Comment> comments;
}
