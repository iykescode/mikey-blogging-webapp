package com.iykescode.blog.mikeybloggingwebapp.repository;

import com.iykescode.blog.mikeybloggingwebapp.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Integer> {

    PostImage findByImage(String image);
}
