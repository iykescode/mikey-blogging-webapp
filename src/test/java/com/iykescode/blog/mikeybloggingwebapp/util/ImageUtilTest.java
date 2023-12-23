package com.iykescode.blog.mikeybloggingwebapp.util;

import com.iykescode.blog.mikeybloggingwebapp.constants.ImageDIRConstant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ImageUtilTest {

    @InjectMocks
    private ImageUtil imageUtil;

    List<Path> filesToBeDeleted = new ArrayList<>();

    @Test
    public void imageUtil_uploadImage() throws IOException {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "filename.jpg", "img/jpeg", file.getBytes());

        String image = imageUtil.uploadImage(mockMultipartFile, ImageDIRConstant.testImages);
        filesToBeDeleted.add(Paths.get(ImageDIRConstant.testImages + image));
        Assertions.assertThat(image).isNotNull();
    }

    @AfterEach
    public void cleanup() {
        filesToBeDeleted.forEach(path -> {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
