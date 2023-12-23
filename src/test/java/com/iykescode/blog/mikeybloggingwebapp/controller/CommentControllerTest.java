package com.iykescode.blog.mikeybloggingwebapp.controller;

import com.iykescode.blog.mikeybloggingwebapp.dto.*;
import com.iykescode.blog.mikeybloggingwebapp.model.*;
import com.iykescode.blog.mikeybloggingwebapp.service.CommentService;
import com.iykescode.blog.mikeybloggingwebapp.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private CommentService commentService;

    private Comment comment;
    private PostDTO postDTO;

    @BeforeEach
    public void init() {
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
        Post post = Post.builder()
                .id(1)
                .title("This is a title")
                .slug("This-is-a-slug")
                .quote("This is a quote")
                .tags("This,Is,A,Tag")
                .category(category)
                .content("This is a content")
                .build();
        Person person = Person.builder()
                .id(1)
                .firstName("Michael")
                .lastName("Jordan")
                .email("michaeljordan@yahoo.com")
                .username("michael_jordan")
                .password("admin")
                .role(role)
                .build();
        comment = Comment.builder()
                .id(1)
                .content("This is a comment content")
                .person(person)
                .post(post)
                .build();
    }

    @Test
    public void commentController_createComment() throws Exception {
        when(postService.findByPostSlug(ArgumentMatchers.any(String.class))).thenReturn(postDTO);
        given(commentService.createComment(ArgumentMatchers.any(CommentDTO.class), ArgumentMatchers.any(PostDTO.class))).willAnswer(invocationOnMock -> true);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("content", comment.getContent());

        mockMvc.perform(post(String.format("/blog/add-comment/%s", comment.getPost().getSlug()))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/blog/view/%s?add-comment=true", comment.getPost().getSlug())));
    }

    @Test
    public void commentController_deleteComment() throws Exception {
        doNothing().when(commentService).deleteByCommentId(ArgumentMatchers.any(Integer.class));

        mockMvc.perform(delete(String.format("/blog/%s/delete-comment/%s", comment.getPost().getSlug(), comment.getId()))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/blog/view/%s?delete-comment=true", comment.getPost().getSlug())));
    }
}
