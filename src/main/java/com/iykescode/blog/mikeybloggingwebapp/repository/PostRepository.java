package com.iykescode.blog.mikeybloggingwebapp.repository;

import com.iykescode.blog.mikeybloggingwebapp.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Optional<Post> findBySlug(String slug);

    List<Post> findByStatus(String status);

    Page<Post> findAllByStatus(String status, Pageable pageable);

    boolean existsBySlug(String slug);

    @Query("SELECT p FROM Post p WHERE LOWER(p.tags) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Post> findPostTagsContainingKeyword(@Param("keyword") String keyword);
}
