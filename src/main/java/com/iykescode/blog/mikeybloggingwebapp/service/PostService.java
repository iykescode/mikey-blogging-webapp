package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.PostDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    void deleteByPostId(Integer id);
    PostDTO findByPostId(Integer id);
    List<PostDTO> findByPostStatus(String status);
    Page<PostDTO> getPaginatedAndSortedProducts(int pageNo, int pageSize, String sortBy);
    Page<PostDTO> getPaginatedAndSortedProductsBySearch(int pageNo, int pageSize, String sortBy, String keyword);
    Page<PostDTO> getLatestPosts();
    PostDTO findByPostSlug(String slug);
    boolean approvePost(PostDTO postDTO);
    boolean draftPost(PostDTO postDTO);
    boolean existsByPostTag(String tags);
    boolean rePublishPost(PostDTO postDTO);
    boolean disapprovePost(PostDTO formPost, Integer id);
    PostDTO savePost(Post post);
    boolean updatePost(PostDTO formPost, Integer id);
    boolean createNewPost(PostDTO postDTO);
}
