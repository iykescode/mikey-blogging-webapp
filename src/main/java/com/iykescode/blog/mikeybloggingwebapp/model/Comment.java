package com.iykescode.blog.mikeybloggingwebapp.model;

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
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "This field can't be blank !!")
    @Column(name = "content")
    private String content;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Person.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Person person;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Post.class)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;
}
