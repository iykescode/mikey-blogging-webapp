package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.*;
import com.iykescode.blog.mikeybloggingwebapp.model.*;
import com.iykescode.blog.mikeybloggingwebapp.repository.CommentRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.impl.CommentServiceImpl;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private EntityMapperService entityMapperService;
    @Mock
    private PersonService personService;
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private CommentServiceImpl commentService;

    private CommentDTO commentDTO;
    private Comment comment;
    private Comment comment2;
    private Comment comment3;
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
        commentDTO = CommentDTO.builder()
                .id(1)
                .content("This is a comment content")
                .person(personDTO)
                .post(postDTO)
                .build();
        comment = Comment.builder()
                .id(1)
                .content("This is a comment content")
                .person(person)
                .post(post)
                .build();
        comment2 = Comment.builder()
                .id(2)
                .content("This is a comment 2 content")
                .person(person)
                .post(post)
                .build();
        comment3 = Comment.builder()
                .id(3)
                .content("This is a comment 3 content")
                .person(person)
                .post(post)
                .build();
    }

    @Test
    public void commentService_findAllSortByDesc() {
        List<Comment> comments = Arrays.asList(
                comment,
                comment2,
                comment3
        );

        when(commentRepository.findAll(Mockito.any(Sort.class))).thenReturn(comments);

        List<CommentDTO> allSortByDesc = commentService.findAllSortByDesc("createdAt");

        Assertions.assertThat(allSortByDesc).size().isGreaterThanOrEqualTo(1);
    }

    @Test
    public void commentService_deleteByCommentId() {
        when(commentRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(comment));
        doNothing().when(commentRepository).delete(Mockito.any(Comment.class));

        assertAll(() -> commentService.deleteByCommentId(commentDTO.getId()));
    }

    @Test
    public void commentService_findByCommentId() {
        when(entityMapperService.mapCommentToDTO(Mockito.any(Comment.class))).thenReturn(commentDTO);
        when(commentRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(comment));

        CommentDTO byCommentId = commentService.findByCommentId(commentDTO.getId());

        Assertions.assertThat(byCommentId).isNotNull();
    }

    @Test
    public void commentService_findCommentByPost() {
        List<Comment> comments = Arrays.asList(
                comment,
                comment2,
                comment3
        );

        when(entityMapperService.mapDTOToPost(Mockito.any(PostDTO.class))).thenReturn(post);
        when(commentRepository.findByPost(post)).thenReturn(comments);

        List<CommentDTO> commentByPost = commentService.findCommentByPost(postDTO);

        Assertions.assertThat(commentByPost).size().isGreaterThanOrEqualTo(1);
    }

    @Test
    public void commentService_createComment() {
        when(personService.getLoggedInUser()).thenReturn(personDTO);
        when(entityMapperService.mapDTOToComment(Mockito.any(CommentDTO.class))).thenReturn(comment);
        when(entityMapperService.mapDTOToPost(Mockito.any(PostDTO.class))).thenReturn(post);
        when(entityMapperService.mapDTOToPerson(Mockito.any(PersonDTO.class))).thenReturn(person);
        when(commentRepository.saveAndFlush(Mockito.any(Comment.class))).thenReturn(comment);

        boolean isSaved = commentService.createComment(commentDTO, postDTO);

        Assertions.assertThat(isSaved).isTrue();
    }
}
