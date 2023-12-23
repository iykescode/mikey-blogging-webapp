package com.iykescode.blog.mikeybloggingwebapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iykescode.blog.mikeybloggingwebapp.annotation.FieldsValueMatchValidator;
import com.iykescode.blog.mikeybloggingwebapp.annotation.UniqueEmailValidator;
import com.iykescode.blog.mikeybloggingwebapp.annotation.UniqueUsernameValidator;
import com.iykescode.blog.mikeybloggingwebapp.validation.BasicFieldValidation;
import com.iykescode.blog.mikeybloggingwebapp.validation.EmailFieldValidation;
import com.iykescode.blog.mikeybloggingwebapp.validation.PasswordFieldValidation;
import com.iykescode.blog.mikeybloggingwebapp.validation.UsernameFieldValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@FieldsValueMatchValidator(
        field = "password",
        fieldMatch = "confirmPassword",
        message = "Passwords do not match !!"
)
public class Person extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native")
    @Column(name = "id")
    private Integer id;

    @Size(message = "This field must be 3 characters or more !!",
            min = 3, groups = { BasicFieldValidation.class, Default.class })
    @Column(name = "first_name")
    private String firstName;

    @Size(message = "This field must be 3 characters or more !!",
            min = 3, groups = { BasicFieldValidation.class, Default.class })
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "This field can't be empty !!",
            groups = { EmailFieldValidation.class, Default.class })
    @Email(message = "A valid email is required",
            groups = { EmailFieldValidation.class, Default.class })
    @UniqueEmailValidator(groups = { EmailFieldValidation.class, Default.class })
    @Column(name = "email")
    private String email;

    @Size(message = "This field must be between 6 - 20 characters !!",
            min = 6, max = 20, groups = { UsernameFieldValidation.class, Default.class })
    @Pattern(message = "This field can only contain letters [a-z][A-Z] and special characters[._]",
            regexp = "^[a-zA-Z._]{6,20}$", groups = { UsernameFieldValidation.class, Default.class })
    @UniqueUsernameValidator(groups = { UsernameFieldValidation.class, Default.class })
    @Column(name = "username")
    private String username;

    @Size(message = "This field must be between 8 - 20 characters !!",
            min = 8, max = 20, groups = { PasswordFieldValidation.class, Default.class })
    @Pattern(message = "Password must contain at least one upper case {A-Z}, " +
            "one lower case {a-z}, " +
            "one digit {0-9} and " +
            "one special character {#?!@$%^&*-}",
            regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$",
            groups = { PasswordFieldValidation.class, Default.class })
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Transient
    @NotBlank(message = "This field can't be empty !!",
            groups = { PasswordFieldValidation.class, Default.class })
    @JsonIgnore
    private String confirmPassword;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = Role.class)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @ManyToOne(cascade = CascadeType.REMOVE, targetEntity = PersonImage.class)
    @JoinColumn(name = "user_image_id", referencedColumnName = "id")
    private PersonImage personImage;

    @ManyToOne(cascade = CascadeType.REMOVE, targetEntity = PersonProfile.class)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    private PersonProfile personProfile;
}
