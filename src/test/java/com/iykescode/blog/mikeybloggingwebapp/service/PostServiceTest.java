package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.CategoryDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.RoleDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Category;
import com.iykescode.blog.mikeybloggingwebapp.model.Person;
import com.iykescode.blog.mikeybloggingwebapp.model.Post;
import com.iykescode.blog.mikeybloggingwebapp.model.Role;
import com.iykescode.blog.mikeybloggingwebapp.repository.PostRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.impl.PostServiceImpl;
import com.iykescode.blog.mikeybloggingwebapp.util.SlugUtil;
import com.iykescode.blog.mikeybloggingwebapp.util.StringUtil;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PersonService personService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private TagService tagService;
    @Mock
    private SlugUtil slugUtil;
    @Mock
    private StringUtil stringUtil;
    @Mock
    private EntityMapperService entityMapperService;
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PostServiceImpl postService;

    private Category category;
    private CategoryDTO categoryDTO;
    private PostDTO formPostDTO;
    private PostDTO postDTO;
    private PostDTO postDTO2;
    private PostDTO postDTO3;
    private Post formPost;
    private Post post;
    private Post post2;
    private Post post3;
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
        categoryDTO = CategoryDTO.builder()
                .id(1)
                .name("Category")
                .build();
        category = Category.builder()
                .id(1)
                .name("Category")
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
                .build();
        postDTO2 = PostDTO.builder()
                .id(2)
                .title("This is a title 2")
                .slug("this-is-a-slug-2")
                .quote("This is a quote 2")
                .tags("This,Is,A,Tag,2")
                .status("DRAFT")
                .category(categoryDTO)
                .content("This is a content 2")
                .build();
        postDTO3 = PostDTO.builder()
                .id(3)
                .title("This is a title 3")
                .slug("this-is-a-slug-3")
                .quote("This is a quote 3")
                .tags("This,Is,A,Tag,3")
                .status("PENDING")
                .category(categoryDTO)
                .content("This is a content 3")
                .build();
        formPost = Post.builder()
                .id(1)
                .title("This is a title form")
                .slug("this-is-a-slug-form")
                .quote("This is a quote")
                .tags("This,Is,A,Tag")
                .newTag("Space, Rocket")
                .status("PENDING")
                .statusMessageDetails("Please upload an image for this post for us to approve it.")
                .category(category)
                .formCategory(1)
                .content("This is a content")
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
    }

    @Test
    public void postService_deleteByPostId() {
        when(postRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(post));
        doNothing().when(postRepository).delete(Mockito.any(Post.class));
        assertAll(() -> postService.deleteByPostId(postDTO.getId()));
    }

    @Test
    public void postService_findByPostId() {
        when(postRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(post2));
        when(entityMapperService.mapPostToDTO(Mockito.any(Post.class))).thenReturn(postDTO);

        PostDTO byPostId = postService.findByPostId(post2.getId());
        Assertions.assertThat(byPostId).isNotNull();
    }

    @Test
    public void postService_findByPostStatus() {
        List<Post> posts = Arrays.asList(
                post,
                post2,
                post3
        );

        when(postRepository.findByStatus(Mockito.any(String.class))).thenReturn(posts);

        List<PostDTO> byPostStatus = postService.findByPostStatus(post.getStatus());
        Assertions.assertThat(byPostStatus).size().isGreaterThanOrEqualTo(1);
    }


    @Test
    public void postService_getPaginatedAndSortedProducts() {
        List<Post> posts = Arrays.asList(
                post,
                post2,
                post3
        );

        PageImpl<Post> postPage = new PageImpl<>(posts);

        when(postRepository.findAllByStatus(Mockito.any(String.class), Mockito.any(Pageable.class))).thenReturn(postPage);
        Page<PostDTO> paginatedAndSortedProducts = postService.getPaginatedAndSortedProducts(1, 10, "status");

        Assertions.assertThat(paginatedAndSortedProducts).size().isGreaterThanOrEqualTo(1);
    }

    @Test
    public void postService_getPaginatedAndSortedProductsBySearch() {
        List<Post> posts = Arrays.asList(
                post,
                post2,
                post3
        );

        PageImpl<Post> postPage = new PageImpl<>(posts);

        when(stringUtil.processString(Mockito.any(String.class))).thenReturn(formPost.getTags());
        when(postRepository.findAllByStatus(Mockito.any(String.class), Mockito.any(Pageable.class))).thenReturn(postPage);
        Page<PostDTO> paginatedAndSortedProductsBySearch = postService.getPaginatedAndSortedProductsBySearch(1, 10, "status", formPost.getTags());

        Assertions.assertThat(paginatedAndSortedProductsBySearch).size().isGreaterThanOrEqualTo(1);
    }

    @Test
    public void postService_getLatestPosts() {
        List<Post> posts = Arrays.asList(
                post,
                post2,
                post3
        );

        PageImpl<Post> postPage = new PageImpl<>(posts);

        when(postRepository.findAll(Mockito.any(Pageable.class))).thenReturn(postPage);
        Page<PostDTO> latestPosts = postService.getLatestPosts();

        Assertions.assertThat(latestPosts).size().isGreaterThanOrEqualTo(1);
    }

    @Test
    public void postService_findByPostSlug() {
        when(postRepository.findBySlug(Mockito.any(String.class))).thenReturn(Optional.ofNullable(post));
        when(entityMapperService.mapPostToDTO(Mockito.any(Post.class))).thenReturn(postDTO);

        PostDTO byPostSlug = postService.findByPostSlug(post.getSlug());

        Assertions.assertThat(byPostSlug).isNotNull();
    }

    @Test
    public void postService_approvePost() {
        when(postRepository.saveAndFlush(Mockito.any(Post.class))).thenReturn(post2);
        when(entityMapperService.mapDTOToPost(Mockito.any(PostDTO.class))).thenReturn(post2);

        boolean isSaved = postService.approvePost(postDTO2);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void postService_draftPost() {
        when(postRepository.saveAndFlush(Mockito.any(Post.class))).thenReturn(post3);
        when(entityMapperService.mapDTOToPost(Mockito.any(PostDTO.class))).thenReturn(post3);

        boolean isSaved = postService.draftPost(postDTO3);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void postService_existsByPostTag() {
        List<Post> posts = Arrays.asList(
                post,
                post2,
                post3
        );

        when(postRepository.findPostTagsContainingKeyword(Mockito.any(String.class))).thenReturn(posts);

        boolean existsByPostTag = postService.existsByPostTag(formPost.getTags());

        Assertions.assertThat(existsByPostTag).isTrue();
    }

    @Test
    public void postService_rePublishPost() {
        when(postRepository.saveAndFlush(Mockito.any(Post.class))).thenReturn(post);
        when(entityMapperService.mapDTOToPost(Mockito.any(PostDTO.class))).thenReturn(post);

        boolean isSaved = postService.rePublishPost(postDTO);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void postService_disapprovePost() {
        when(postRepository.saveAndFlush(Mockito.any(Post.class))).thenReturn(formPost);
        when(postRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(formPost));

        boolean isSaved = postService.disapprovePost(formPostDTO, formPostDTO.getId());

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void postService_savePost() {
        when(postRepository.saveAndFlush(Mockito.any(Post.class))).thenReturn(formPost);
        when(entityMapperService.mapPostToDTO(Mockito.any(Post.class))).thenReturn(formPostDTO);

        PostDTO isSaved = postService.savePost(formPost);

        Assertions.assertThat(isSaved).isNotNull();
    }

    @Test
    public void postService_updatePost() {
        when(postRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(formPost));
        when(stringUtil.processString(Mockito.any(String.class))).thenReturn(formPost.getNewTag());
        when(categoryService.findByCategoryId(Mockito.any(Integer.class))).thenReturn(categoryDTO);
        when(entityMapperService.mapDTOToCategory(Mockito.any(CategoryDTO.class))).thenReturn(category);
        when(postRepository.saveAndFlush(Mockito.any(Post.class))).thenReturn(formPost);

        boolean isSaved = postService.updatePost(formPostDTO, formPost.getId());

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void postService_createNewPost() {
        when(stringUtil.processString(Mockito.any(String.class))).thenReturn(formPost.getNewTag());
        when(personService.getLoggedInUser()).thenReturn(personDTO);
        when(categoryService.findByCategoryId(Mockito.any(Integer.class))).thenReturn(categoryDTO);
        when(entityMapperService.mapDTOToCategory(Mockito.any(CategoryDTO.class))).thenReturn(category);
        when(entityMapperService.mapDTOToPost(Mockito.any(PostDTO.class))).thenReturn(formPost);
        when(entityMapperService.mapDTOToPerson(Mockito.any(PersonDTO.class))).thenReturn(person);
        when(postRepository.saveAndFlush(Mockito.any(Post.class))).thenReturn(formPost);

        boolean isSaved = postService.createNewPost(formPostDTO);

        Assertions.assertThat(isSaved).isTrue();
    }
}
