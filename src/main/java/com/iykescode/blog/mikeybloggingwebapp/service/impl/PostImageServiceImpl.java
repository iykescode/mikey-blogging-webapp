package com.iykescode.blog.mikeybloggingwebapp.service.impl;

import com.iykescode.blog.mikeybloggingwebapp.constants.ImageDIRConstant;
import com.iykescode.blog.mikeybloggingwebapp.constants.PostStatus;
import com.iykescode.blog.mikeybloggingwebapp.constants.PostStatusMessageConstant;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostImageDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Post;
import com.iykescode.blog.mikeybloggingwebapp.model.PostImage;
import com.iykescode.blog.mikeybloggingwebapp.repository.PostImageRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.EntityMapperService;
import com.iykescode.blog.mikeybloggingwebapp.service.PostImageService;
import com.iykescode.blog.mikeybloggingwebapp.service.PostService;
import com.iykescode.blog.mikeybloggingwebapp.util.ImageUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@AllArgsConstructor
public class PostImageServiceImpl implements PostImageService {

    private final PostImageRepository postImageRepository;
    private final PostService postService;
    private final ImageUtil imageUtil;
    private final EntityMapperService entityMapperService;

    @Override
    public boolean createNewImage(PostDTO postDTO, PostImageDTO postImageDTO) throws IOException {
        boolean isSaved = false;
        String uploadedImage = imageUtil.uploadImage(postImageDTO.getImageContent(), ImageDIRConstant.postImages);

        PostImage postImage = entityMapperService.mapDTOToPostImage(postImageDTO);
        postImage.setImage(uploadedImage);
        PostImage savedImage = postImageRepository.saveAndFlush(postImage);

        Post post = entityMapperService.mapDTOToPost(postDTO);

        if(savedImage.getId() > 0) {
            setPostStatusMessage(postImage, post);
        }

        PostDTO savedPost = postService.savePost(post);

        if (savedPost.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean updateImage(PostImageDTO postImage, PostImageDTO dbPostImage) throws IOException {
        boolean isSaved = false;
        String uploadedImage = imageUtil.uploadImage(postImage.getImageContent(), ImageDIRConstant.postImages);

        File file = new File(ImageDIRConstant.postImages + dbPostImage.getImage());
        if (file.exists()) {
            file.delete();
        }

        PostImage mappedPostImage = entityMapperService.mapDTOToPostImage(dbPostImage);
        mappedPostImage.setImage(uploadedImage);
        PostImage savedImage = postImageRepository.saveAndFlush(mappedPostImage);

        if (savedImage.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    private void setPostStatusMessage(PostImage image, Post post) {
        post.setPostImage(image);
        post.setStatus(PostStatus.PENDING.name());
        post.setStatusMessage(PostStatusMessageConstant.PendingMessage);
        post.setStatusMessageDetails(null);
    }
}
