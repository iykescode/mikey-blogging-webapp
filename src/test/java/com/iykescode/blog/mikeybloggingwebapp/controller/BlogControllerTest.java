package com.iykescode.blog.mikeybloggingwebapp.controller;

import com.iykescode.blog.mikeybloggingwebapp.constants.UrlPathConstant;
import com.iykescode.blog.mikeybloggingwebapp.dto.*;
import com.iykescode.blog.mikeybloggingwebapp.model.*;
import com.iykescode.blog.mikeybloggingwebapp.repository.CommentRepository;
import com.iykescode.blog.mikeybloggingwebapp.repository.PersonRepository;
import com.iykescode.blog.mikeybloggingwebapp.repository.PostRepository;
import com.iykescode.blog.mikeybloggingwebapp.repository.PostViewRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.CommentService;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonService;
import com.iykescode.blog.mikeybloggingwebapp.service.PostService;
import com.iykescode.blog.mikeybloggingwebapp.service.PostViewService;
import com.iykescode.blog.mikeybloggingwebapp.util.RedirectMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BlogController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private PostService postService;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PersonService personService;

    @MockBean
    private PostViewRepository postViewRepository;

    @MockBean
    private PostViewService postViewService;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private CommentService commentService;

    @MockBean
    private UrlPathConstant urlPathConstant;

    private PostDTO postDTO;
    private Post post;
    private Post post2;
    private Post post3;

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
        PersonProfile personProfile = PersonProfile.builder()
                .id(1)
                .headline("This is a simple headline")
                .summary("This is a simple summary")
                .facebook("https://www.facebook.com")
                .twitter("https://www.twitter.com")
                .instagram("https://www.instagram.com")
                .linkedIn("https://www.linkedin.com")
                .build();
        PersonProfileDTO personProfileDTO = PersonProfileDTO.builder()
                .id(1)
                .headline("This is a simple headline")
                .summary("This is a simple summary")
                .facebook("https://www.facebook.com")
                .twitter("https://www.twitter.com")
                .instagram("https://www.instagram.com")
                .linkedIn("https://www.linkedin.com")
                .build();
        PersonImage personImage = PersonImage.builder()
                .id(1)
                .image("image.jpg")
                .build();
        PersonImageDTO personImageDTO = PersonImageDTO.builder()
                .id(1)
                .image("image.jpg")
                .build();
        PersonDTO personDTO = PersonDTO.builder()
                .id(1)
                .firstName("Michael")
                .lastName("Jordan")
                .email("michaeljordan@yahoo.com")
                .username("michael_jordan")
                .password("admin").
                confirmPassword("admin")
                .role(roleDTO)
                .personProfile(personProfileDTO)
                .personImage(personImageDTO)
                .build();
        Person person = Person.builder()
                .id(1)
                .firstName("Michael")
                .lastName("Jordan")
                .email("michaeljordan@yahoo.com")
                .username("michael_jordan")
                .password("admin")
                .role(role)
                .personProfile(personProfile)
                .personImage(personImage)
                .build();
        PostImage postImage = PostImage.builder()
                .id(1)
                .image("image.jpg")
                .build();
        PostImageDTO postImageDTO = PostImageDTO.builder()
                .id(1)
                .image("image.jpg")
                .build();
        postDTO = PostDTO.builder()
                .id(1)
                .title("This is a title")
                .slug("this-is-a-slug")
                .quote("This is a quote")
                .tags("This,Is,A,Tag")
                .status("PUBLISHED")
                .category(categoryDTO)
                .content("This is a content")
                .postImage(postImageDTO)
                .person(personDTO)
                .build();
        post = Post.builder()
                .id(1)
                .title("This is a title")
                .slug("this-is-a-slug")
                .quote("This is a quote")
                .tags("This,Is,A,Tag")
                .status("PUBLISHED")
                .category(category)
                .content("This is a content")
                .postImage(postImage)
                .person(person)
                .build();
        post2 = Post.builder()
                .id(2)
                .title("This is a title 2")
                .slug("this-is-a-slug-2")
                .quote("This is a quote 2")
                .tags("This,Is,A,Tag,2")
                .status("PENDING")
                .category(category)
                .content("This is a content 2")
                .build();
        post3 = Post.builder()
                .id(3)
                .title("This is a title 3")
                .slug("this-is-a-slug-3")
                .quote("This is a quote 3")
                .tags("This,Is,A,Tag,3")
                .status("DRAFT")
                .category(category)
                .content("This is a content 3")
                .build();
    }

    @Test
    public void blogController_BlogWithSideBar() throws Exception {
        List<Post> posts = Arrays.asList(
                post,
                post2,
                post3
        );

        PageImpl<Post> postPage = new PageImpl<>(posts);

        given(postService.getPaginatedAndSortedProducts(ArgumentMatchers.any(Integer.class), ArgumentMatchers.any(Integer.class), ArgumentMatchers.any(String.class)))
                .willAnswer(invocationOnMock -> postPage);

        mockMvc.perform(get("/blog/blog-with-sidebar"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("blog/blog-with-sidebar"));
    }

    @Test
    public void blogController_BlogWithSideBarBySearch() throws Exception {
        List<Post> posts = Arrays.asList(
                post,
                post2,
                post3
        );

        PageImpl<Post> postPage = new PageImpl<>(posts);

        given(postService.getPaginatedAndSortedProductsBySearch(ArgumentMatchers.any(Integer.class), ArgumentMatchers.any(Integer.class), ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class)))
                .willAnswer(invocationOnMock -> postPage);

        mockMvc.perform(get("/blog/search/tag"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("blog/blog-with-sidebar"));
    }

    @Test
    public void blogController_SearchBlog() throws Exception {
        mockMvc.perform(post("/blog/search")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("keyword", "tag"))
                .andExpect(status().is3xxRedirection())
                .andExpect(RedirectMatchers.redirectedUrlIsOneOf("/blog/search/tag", "/blog/blog-with-sidebar"));
    }

    @Test
    public void blogController_BlogSingle() throws Exception {
        given(postService.findByPostSlug(ArgumentMatchers.any(String.class))).willAnswer(invocationOnMock -> postDTO);

        mockMvc.perform(get("/blog/view/this-is-a-title"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("blog/blog-single"));
    }
}
