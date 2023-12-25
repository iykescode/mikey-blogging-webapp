package com.iykescode.blog.mikeybloggingwebapp.controller;

import com.iykescode.blog.mikeybloggingwebapp.constants.UrlPathConstant;
import com.iykescode.blog.mikeybloggingwebapp.dto.CategoryDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostImageDTO;
import com.iykescode.blog.mikeybloggingwebapp.repository.*;
import com.iykescode.blog.mikeybloggingwebapp.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagRepository tagRepository;

    @MockBean
    private TagService tagService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private PostService postService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private PostImageRepository postImageRepository;

    @MockBean
    private PostImageService postImageService;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PersonService personService;

    @MockBean
    private UrlPathConstant urlPathConstant;

    private PostDTO formPostDTO;
    private PostDTO postDTO;

    @BeforeEach
    public void init() {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(1)
                .name("Category")
                .build();
        PostImageDTO postImageDTO = PostImageDTO.builder()
                .id(1)
                .image("image.jpg")
                .build();
        formPostDTO = PostDTO.builder()
                .id(1)
                .title("This is a title")
                .slug("this-is-a-slug")
                .quote("This is a quote")
                .tags("This,Is,A,Tag")
                .newTag("Space, Rocket")
                .status("PENDING")
                .statusMessageDetails("Please upload an image for this post for us to approve it.")
                .category(categoryDTO)
                .formCategory(1)
                .content("This is a content")
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
                .build();
    }

    @Test
    public void postController_viewAllPosts() throws Exception {
        mockMvc.perform(get("/user/posts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/posts"));
    }

    @Test
    public void postController_viewPendingPosts() throws Exception {
        mockMvc.perform(get("/user/posts/pending"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/pending-posts"));
    }

    @Test
    public void postController_viewDraftPosts() throws Exception {
        mockMvc.perform(get("/user/posts/drafts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/draft-posts"));
    }

    @Test
    public void postController_createPostPage() throws Exception {
        mockMvc.perform(get("/user/posts/create"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/post-form"));
    }

    @Test
    public void postController_createPostImagePage() throws Exception {
        given(postService.findByPostSlug(ArgumentMatchers.any(String.class))).willAnswer(invocationOnMock -> postDTO);

        mockMvc.perform(get(String.format("/user/posts/upload-image/%s", postDTO.getSlug())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/image-form"));
    }

    @Test
    public void postController_editPostImagePage() throws Exception {
        given(postService.findByPostSlug(ArgumentMatchers.any(String.class))).willAnswer(invocationOnMock -> postDTO);

        mockMvc.perform(get(String.format("/user/posts/update-image/%s", postDTO.getSlug())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/image-form"));
    }

    @Test
    public void postController_editPost() throws Exception {
        given(postService.findByPostSlug(ArgumentMatchers.any(String.class))).willAnswer(invocationOnMock -> postDTO);

        mockMvc.perform(get(String.format("/user/posts/edit/%s", postDTO.getSlug())))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/post-form"));
    }

    @Test
    public void postController_createPost() throws Exception {
        given(postService.createNewPost(ArgumentMatchers.any(PostDTO.class))).willAnswer(invocationOnMock -> true);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", formPostDTO.getTitle());
        params.add("quote", formPostDTO.getQuote());
        params.add("tags", formPostDTO.getTags());
        params.add("newTag", formPostDTO.getNewTag());
        params.add("formCategory", formPostDTO.getFormCategory().toString());
        params.add("content", formPostDTO.getContent());

        mockMvc.perform(post("/user/posts/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/posts/pending?message=created"));
    }

    @Test
    public void postController_uploadPostImage() throws Exception {
        given(postService.findByPostSlug(ArgumentMatchers.any(String.class))).willAnswer(invocationOnMock -> postDTO);
        given(postImageService.updateImage(ArgumentMatchers.any(PostImageDTO.class), ArgumentMatchers.any(PostImageDTO.class))).willAnswer(invocationOnMock -> true);
        given(postImageService.createNewImage(ArgumentMatchers.any(PostDTO.class), ArgumentMatchers.any(PostImageDTO.class))).willAnswer(invocationOnMock -> true);

        MultipartFile file = Mockito.mock(MultipartFile.class);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("imageContent", "filename.png", "image/png", file.getBytes());

        mockMvc.perform(multipart(String.format("/user/posts/upload-image/%s", postDTO.getSlug()))
                        .file(mockMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/posts/pending?message=image-uploaded"));
    }

    @Test
    public void postController_updatePost() throws Exception {
        given(postService.updatePost(ArgumentMatchers.any(PostDTO.class), ArgumentMatchers.any(Integer.class))).willAnswer(invocationOnMock -> true);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", formPostDTO.getTitle());
        params.add("quote", formPostDTO.getQuote());
        params.add("tags", formPostDTO.getTags());
        params.add("newTag", formPostDTO.getNewTag());
        params.add("formCategory", formPostDTO.getFormCategory().toString());
        params.add("content", formPostDTO.getContent());

        mockMvc.perform(patch(String.format("/user/posts/update/%s", postDTO.getId()))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/posts/pending?message=updated"));
    }

    @Test
    public void postController_approvePost() throws Exception {
        given(postService.findByPostId(ArgumentMatchers.any(Integer.class))).willAnswer(invocationOnMock -> postDTO);
        given(postService.approvePost(ArgumentMatchers.any(PostDTO.class))).willAnswer(invocationOnMock -> true);

        mockMvc.perform(patch(String.format("/user/posts/approve/%s", postDTO.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/posts/pending?message=approved"));
    }

    @Test
    public void postController_draftPost() throws Exception {
        given(postService.findByPostId(ArgumentMatchers.any(Integer.class))).willAnswer(invocationOnMock -> postDTO);
        given(postService.draftPost(ArgumentMatchers.any(PostDTO.class))).willAnswer(invocationOnMock -> true);

        mockMvc.perform(patch(String.format("/user/posts/draft/%s", postDTO.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/posts?message=drafted"));
    }

    @Test
    public void postController_deletePost() throws Exception {
        doNothing().when(postService).deleteByPostId(ArgumentMatchers.any(Integer.class));

        mockMvc.perform(delete(String.format("/user/posts/delete/%s", postDTO.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/posts/drafts?message=deleted"));
    }

    @Test
    public void postController_disapprovePost() throws Exception {
        given(postService.disapprovePost(ArgumentMatchers.any(PostDTO.class), ArgumentMatchers.any(Integer.class))).willAnswer(invocationOnMock -> true);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("statusMessageDetails", formPostDTO.getStatusMessageDetails());

        mockMvc.perform(patch(String.format("/user/posts/disapprove/%s", postDTO.getId()))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/posts/pending?message=disapproved"));
    }



    @Test
    public void postController_rePublishPost() throws Exception {
        given(postService.findByPostId(ArgumentMatchers.any(Integer.class))).willAnswer(invocationOnMock -> postDTO);
        given(postService.rePublishPost(ArgumentMatchers.any(PostDTO.class))).willAnswer(invocationOnMock -> true);

        mockMvc.perform(patch(String.format("/user/posts/republish/%s", postDTO.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/posts/pending?message=republished"));
    }
}
