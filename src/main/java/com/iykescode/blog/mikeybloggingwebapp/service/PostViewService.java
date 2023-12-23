package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostViewDTO;

import java.util.List;

public interface PostViewService {

    void createPostView(PostDTO post, PersonDTO person);
    boolean exists(PersonDTO person, PostDTO post);
    List<PostViewDTO> findByPost(PostDTO post);
}
