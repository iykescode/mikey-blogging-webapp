package com.iykescode.blog.mikeybloggingwebapp.service.impl;

import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostViewDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Person;
import com.iykescode.blog.mikeybloggingwebapp.model.Post;
import com.iykescode.blog.mikeybloggingwebapp.model.PostView;
import com.iykescode.blog.mikeybloggingwebapp.repository.PostViewRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.EntityMapperService;
import com.iykescode.blog.mikeybloggingwebapp.service.PostViewService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostViewServiceImpl implements PostViewService {

    private final PostViewRepository postViewRepository;
    private final EntityMapperService entityMapperService;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void createPostView(PostDTO post, PersonDTO person) {
        if(!exists(person, post)) {
            PostView view = new PostView();
            Person mappedPerson = entityMapperService.mapDTOToPerson(person);
            Post mappedPost = entityMapperService.mapDTOToPost(post);
            view.setPerson(entityManager.merge(mappedPerson));
            view.setPost(entityManager.merge(mappedPost));
            entityManager.persist(view);
            postViewRepository.saveAndFlush(view);
        }
    }

    @Override
    public boolean exists(PersonDTO person, PostDTO post) {
        Person mappedPerson = entityMapperService.mapDTOToPerson(person);
        Post mappedPost = entityMapperService.mapDTOToPost(post);
        return postViewRepository.existsByPersonAndPost(mappedPerson, mappedPost);
    }

    @Override
    public List<PostViewDTO> findByPost(PostDTO post) {
        Post mappedPost = entityMapperService.mapDTOToPost(post);
        List<PostView> postViews = postViewRepository.findByPost(mappedPost);
        return postViews.stream()
                .map(entityMapperService::mapPostViewToDTO)
                .toList();
    }
}
