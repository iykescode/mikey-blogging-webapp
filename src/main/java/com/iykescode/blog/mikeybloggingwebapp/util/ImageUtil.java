package com.iykescode.blog.mikeybloggingwebapp.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class ImageUtil {

    public String uploadImage(MultipartFile file, String imagePath) throws IOException {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID() + extension;

        byte[] bytes = file.getBytes();
        Files.createDirectories(Paths.get(imagePath));
        Path path = Paths.get(imagePath + uniqueFileName);
        Files.write(path, bytes);

        return uniqueFileName;
    }
}