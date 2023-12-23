package com.iykescode.blog.mikeybloggingwebapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iykescode.blog.mikeybloggingwebapp.annotation.FieldsValueMatchValidator;
import com.iykescode.blog.mikeybloggingwebapp.annotation.UniqueEmailValidator;
import com.iykescode.blog.mikeybloggingwebapp.annotation.UniqueUsernameValidator;
import com.iykescode.blog.mikeybloggingwebapp.validation.BasicFieldValidation;
import com.iykescode.blog.mikeybloggingwebapp.validation.EmailFieldValidation;
import com.iykescode.blog.mikeybloggingwebapp.validation.PasswordFieldValidation;
import com.iykescode.blog.mikeybloggingwebapp.validation.UsernameFieldValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldsValueMatchValidator(
        field = "password",
        fieldMatch = "confirmPassword",
        message = "Passwords do not match !!"
)
public class PersonDTO extends BaseEntityDTO {

    private Integer id;

    @Size(message = "This field must be 3 characters or more !!",
            min = 3, groups = { BasicFieldValidation.class, Default.class })
    private String firstName;

    @Size(message = "This field must be 3 characters or more !!",
            min = 3, groups = { BasicFieldValidation.class, Default.class })
    private String lastName;

    @NotBlank(message = "This field can't be empty !!",
            groups = { EmailFieldValidation.class, Default.class })
    @Email(message = "A valid email is required",
            groups = { EmailFieldValidation.class, Default.class })
    @UniqueEmailValidator(groups = { EmailFieldValidation.class, Default.class })
    private String email;

    @Pattern(message = "This field can only contain letters [a-z][A-Z] and special characters[._]",
            regexp = "^[a-zA-Z._]{6,20}$", groups = { UsernameFieldValidation.class, Default.class })
    @UniqueUsernameValidator(groups = { UsernameFieldValidation.class, Default.class })
    private String username;

    @Pattern(message = "Password must contain at least one upper case {A-Z}, " +
            "one lower case {a-z}, " +
            "one digit {0-9} and " +
            "one special character {#?!@$%^&*-}",
            regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$",
            groups = { PasswordFieldValidation.class, Default.class })
    @JsonIgnore
    private String password;

    @NotBlank(message = "This field can't be empty !!",
            groups = { PasswordFieldValidation.class, Default.class })
    @JsonIgnore
    private String confirmPassword;

    private RoleDTO role;

    private PersonImageDTO personImage;

    private PersonProfileDTO personProfile;
}
