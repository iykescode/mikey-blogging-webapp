package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.CommentDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> findAllSortByDesc(String attribute);
    void deleteByCommentId(Integer id);
    CommentDTO findByCommentId(Integer id);
    List<CommentDTO> findCommentByPost(PostDTO postDTO);
    boolean createComment(CommentDTO commentDTO, PostDTO postDTO);
}
