package com.iykescode.blog.mikeybloggingwebapp.validation;

import com.iykescode.blog.mikeybloggingwebapp.annotation.ImageSizeValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImageSizeValidation implements ConstraintValidator<ImageSizeValidator, MultipartFile> {

    private long maxSize;

    @Override
    public void initialize(ImageSizeValidator constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext cxt) {
        return multipartFile == null || multipartFile.getSize() <= maxSize;
    }
}
