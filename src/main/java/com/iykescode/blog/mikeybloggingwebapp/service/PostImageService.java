package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.PostDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostImageDTO;

import java.io.IOException;

public interface PostImageService {

    boolean createNewImage(PostDTO postDTO, PostImageDTO postImageDTO) throws IOException;
    boolean updateImage(PostImageDTO postImage, PostImageDTO dbPostImage) throws IOException;
}
