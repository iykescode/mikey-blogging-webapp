package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.*;
import com.iykescode.blog.mikeybloggingwebapp.model.*;
import com.iykescode.blog.mikeybloggingwebapp.repository.PostImageRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.impl.PostImageServiceImpl;
import com.iykescode.blog.mikeybloggingwebapp.util.ImageUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostImageServiceTest {

    @Mock
    private PostImageRepository postImageRepository;
    @Mock
    private PostService postService;
    @Mock
    private ImageUtil imageUtil;
    @Mock
    private EntityMapperService entityMapperService;

    @InjectMocks
    private PostImageServiceImpl postImageService;

    private final PostImageDTO formPostImage = new PostImageDTO();
    private PostImage postImage;
    private PostImageDTO postImageDTO;
    private PostDTO postDTO;
    private Post post;

    @BeforeEach
    public void init() {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(1)
                .name("Category")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("Category")
                .build();
        postImage = PostImage.builder()
                .id(1)
                .image("image.jpg")
                .build();
        postImageDTO = PostImageDTO.builder()
                .id(1)
                .image("image.jpg")
                .build();
        postDTO = PostDTO.builder()
                .id(1)
                .title("This is a title")
                .slug("This-is-a-slug")
                .quote("This is a quote")
                .tags("This,Is,A,Tag")
                .category(categoryDTO)
                .content("This is a content")
                .build();
        post = Post.builder()
                .id(1)
                .title("This is a title")
                .slug("This-is-a-slug")
                .quote("This is a quote")
                .tags("This,Is,A,Tag")
                .category(category)
                .content("This is a content")
                .build();
    }

    @Test
    public void postImageService_createNewImage() throws IOException {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "filename.jpg", "img/jpeg", file.getBytes());
        formPostImage.setImageContent(mockMultipartFile);

        when(entityMapperService.mapDTOToPostImage(Mockito.any(PostImageDTO.class))).thenReturn(postImage);
        when(entityMapperService.mapDTOToPost(Mockito.any(PostDTO.class))).thenReturn(post);
        when(postService.savePost(Mockito.any(Post.class))).thenReturn(postDTO);
        when(postImageRepository.saveAndFlush(Mockito.any(PostImage.class))).thenReturn(postImage);

        boolean isSaved = postImageService.createNewImage(postDTO, formPostImage);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void postImageService_updateImage() throws IOException {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "filename.jpg", "img/jpeg", file.getBytes());
        formPostImage.setImageContent(mockMultipartFile);

        when(entityMapperService.mapDTOToPostImage(Mockito.any(PostImageDTO.class))).thenReturn(postImage);
        when(postImageRepository.saveAndFlush(Mockito.any(PostImage.class))).thenReturn(postImage);

        boolean isSaved = postImageService.updateImage(formPostImage, postImageDTO);

        Assertions.assertThat(isSaved).isTrue();
    }
}
