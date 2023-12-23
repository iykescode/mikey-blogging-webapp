package com.iykescode.blog.mikeybloggingwebapp.repository;

import com.iykescode.blog.mikeybloggingwebapp.model.Comment;
import com.iykescode.blog.mikeybloggingwebapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByPost(Post post);
}
