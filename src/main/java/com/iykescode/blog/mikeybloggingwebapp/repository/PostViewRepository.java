package com.iykescode.blog.mikeybloggingwebapp.repository;

import com.iykescode.blog.mikeybloggingwebapp.model.Person;
import com.iykescode.blog.mikeybloggingwebapp.model.Post;
import com.iykescode.blog.mikeybloggingwebapp.model.PostView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostViewRepository extends JpaRepository<PostView, Integer> {

    boolean existsByPersonAndPost(Person person, Post post);

    List<PostView> findByPost(Post post);
}
