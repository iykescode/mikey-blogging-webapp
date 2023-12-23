package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.*;
import com.iykescode.blog.mikeybloggingwebapp.model.*;
import com.iykescode.blog.mikeybloggingwebapp.repository.PostViewRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.impl.PostViewServiceImpl;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostViewServiceTest {

    @Mock
    private PostViewRepository postViewRepository;
    @Mock
    private EntityMapperService entityMapperService;
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PostViewServiceImpl postViewService;

    private PostView postView;
    private PostView postView2;
    private PostView postView3;
    private PostDTO postDTO;
    private Post post;
    private PersonDTO personDTO;
    private Person person;

    @BeforeEach
    public void init() {
        RoleDTO roleDTO = RoleDTO.builder()
                .id(1)
                .roleName("ROLE_ADMIN")
                .build();
        Role role = Role.builder()
                .id(1)
                .roleName("ROLE_ADMIN")
                .build();
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(1)
                .name("Category")
                .build();
        Category category = Category.builder()
                .id(1)
                .name("Category")
                .build();
        postDTO = PostDTO.builder()
                .id(1)
                .title("This is a title")
                .slug("This-is-a-slug")
                .quote("This is a quote")
                .tags("This,Is,A,Tag")
                .category(categoryDTO)
                .content("This is a content")
                .build();
        post = Post.builder()
                .id(1)
                .title("This is a title")
                .slug("This-is-a-slug")
                .quote("This is a quote")
                .tags("This,Is,A,Tag")
                .category(category)
                .content("This is a content")
                .build();
        personDTO = PersonDTO.builder()
                .id(1)
                .firstName("Michael")
                .lastName("Jordan")
                .email("michaeljordan@yahoo.com")
                .username("michael_jordan")
                .password("admin").
                confirmPassword("admin")
                .role(roleDTO)
                .build();
        person = Person.builder()
                .id(1)
                .firstName("Michael")
                .lastName("Jordan")
                .email("michaeljordan@yahoo.com")
                .username("michael_jordan")
                .password("admin")
                .role(role)
                .build();
        postView = PostView.builder()
                .id(1)
                .person(person)
                .post(post)
                .build();
        postView2 = PostView.builder()
                .id(2)
                .person(person)
                .post(post)
                .build();
        postView3 = PostView.builder()
                .id(3)
                .person(person)
                .post(post)
                .build();
    }

    @Test
    public void postViewService_createPostView() {
        when(entityMapperService.mapDTOToPerson(Mockito.any(PersonDTO.class))).thenReturn(person);
        when(entityMapperService.mapDTOToPost(Mockito.any(PostDTO.class))).thenReturn(post);
        when(postViewRepository.saveAndFlush(Mockito.any(PostView.class))).thenReturn(postView);

        assertAll(() ->postViewService.createPostView(postDTO, personDTO));
    }

    @Test
    public void postViewService_exists() {
        when(entityMapperService.mapDTOToPerson(Mockito.any(PersonDTO.class))).thenReturn(person);
        when(entityMapperService.mapDTOToPost(Mockito.any(PostDTO.class))).thenReturn(post);
        when(postViewRepository.existsByPersonAndPost(Mockito.any(Person.class), Mockito.any(Post.class))).thenReturn(true);

        boolean exists = postViewService.exists(personDTO, postDTO);

        Assertions.assertThat(exists).isTrue();
    }

    @Test
    public void postViewService_findByPost() {
        List<PostView> postViews = Arrays.asList(
                postView,
                postView2,
                postView3
        );

        when(entityMapperService.mapDTOToPost(Mockito.any(PostDTO.class))).thenReturn(post);
        when(postViewRepository.findByPost(Mockito.any(Post.class))).thenReturn(postViews);

        List<PostViewDTO> byPost = postViewService.findByPost(postDTO);

        Assertions.assertThat(byPost).size().isGreaterThanOrEqualTo(1);
    }
}
