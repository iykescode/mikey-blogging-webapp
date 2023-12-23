package com.iykescode.blog.mikeybloggingwebapp.service.impl;

import com.iykescode.blog.mikeybloggingwebapp.constants.PostStatus;
import com.iykescode.blog.mikeybloggingwebapp.constants.PostStatusMessageConstant;
import com.iykescode.blog.mikeybloggingwebapp.dto.CategoryDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Category;
import com.iykescode.blog.mikeybloggingwebapp.model.Post;
import com.iykescode.blog.mikeybloggingwebapp.model.Tag;
import com.iykescode.blog.mikeybloggingwebapp.repository.PostRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.*;
import com.iykescode.blog.mikeybloggingwebapp.util.SlugUtil;
import com.iykescode.blog.mikeybloggingwebapp.util.StringUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PersonService personService;
    private final CategoryService categoryService;
    private final PostRepository postRepository;
    private final TagService tagService;
    private final SlugUtil slugUtil;
    private final StringUtil stringUtil;
    private final EntityMapperService entityMapperService;
    private final EntityManager entityManager;

    @Override
    public void deleteByPostId(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        postRepository.delete(post);
    }

    @Override
    public PostDTO findByPostId(Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        return entityMapperService.mapPostToDTO(post);
    }

    @Override
    public List<PostDTO> findByPostStatus(String status) {
        List<Post> posts = postRepository.findByStatus(status);
        return posts.stream()
                .map(entityMapperService::mapPostToDTO)
                .toList();
    }

    @Override
    public Page<PostDTO> getPaginatedAndSortedProducts(int pageNo, int pageSize, String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<Post> posts = postRepository.findAllByStatus(PostStatus.PUBLISHED.name(), pageable);
        List<PostDTO> dtoList = posts.getContent().stream()
                .map(entityMapperService::mapPostToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList);
    }

    @Override
    public Page<PostDTO> getPaginatedAndSortedProductsBySearch(int pageNo, int pageSize, String sortBy, String keyword) {
        Sort sort = Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<Post> posts = postRepository.findAllByStatus(PostStatus.PUBLISHED.name(), pageable);
        List<PostDTO> dtoList = posts.getContent().stream()
                .filter(p -> p.getTags().contains(stringUtil.processString(keyword)))
                .map(entityMapperService::mapPostToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList);
    }

    @Override
    public Page<PostDTO> getLatestPosts() {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(0, 3, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<PostDTO> dtoList = posts.getContent().stream()
                .map(entityMapperService::mapPostToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList);
    }

    @Override
    public PostDTO findByPostSlug(String slug) {
        Post post = postRepository.findBySlug(slug).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        return entityMapperService.mapPostToDTO(post);
    }

    @Override
    public boolean approvePost(PostDTO postDTO) {
        boolean isSaved = false;
        postDTO.setStatus(PostStatus.PUBLISHED.name());
        postDTO.setStatusMessage(PostStatusMessageConstant.ApprovedMessage);
        postDTO.setStatusMessageDetails(null);
        Post post = postRepository.saveAndFlush(entityMapperService.mapDTOToPost(postDTO));

        if(post.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean draftPost(PostDTO postDTO) {
        boolean isSaved = false;
        postDTO.setStatus(PostStatus.DRAFT.name());
        postDTO.setStatusMessage(PostStatusMessageConstant.DraftMessage);
        postDTO.setStatusMessageDetails(null);
        Post post = postRepository.saveAndFlush(entityMapperService.mapDTOToPost(postDTO));

        if(post.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean existsByPostTag(String tags) {
        boolean isSaved = false;

        List<Post> postTagsContainingKeyword = postRepository.findPostTagsContainingKeyword(tags);

        if(!postTagsContainingKeyword.isEmpty())
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean rePublishPost(PostDTO postDTO) {
        boolean isSaved = false;
        postDTO.setStatus(PostStatus.PENDING.name());
        postDTO.setStatusMessage(PostStatusMessageConstant.PendingMessage);
        postDTO.setStatusMessageDetails(null);
        Post post = postRepository.saveAndFlush(entityMapperService.mapDTOToPost(postDTO));

        if(post.getId() > 0)
            isSaved = true;

        return isSaved;
    }


    @Override
    public boolean disapprovePost(PostDTO formPost, Integer id) {
        boolean isSaved = false;
        Post dbPost = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        dbPost.setStatus(PostStatus.DRAFT.name());
        dbPost.setStatusMessage(PostStatusMessageConstant.UnapprovedMessage);
        dbPost.setStatusMessageDetails(formPost.getStatusMessageDetails());
        Post post = postRepository.saveAndFlush(dbPost);

        if(post.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public PostDTO savePost(Post post) {
        Post savedPost = postRepository.saveAndFlush(post);
        return entityMapperService.mapPostToDTO(savedPost);
    }

    @Override
    @Transactional
    public boolean updatePost(PostDTO formPost, Integer id) {
        boolean isSaved = false;
        Post dbPost = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
        String slug = dbPost.getSlug();
        String postTags = dbPost.getTags();
        Category category = dbPost.getCategory();
        CategoryDTO formCategoryDTO = categoryService.findByCategoryId(formPost.getFormCategory());
        Category formCategory = entityMapperService.mapDTOToCategory(formCategoryDTO);

        if(!Objects.equals(dbPost.getTitle(), formPost.getTitle())) {
            slug = slugUtil.createSlug(formPost.getTitle());

            if(postRepository.existsBySlug(slug)) {
                updatePost(formPost, id);
            }
        }

        if(!Objects.equals(formPost.getTags(), "")) {
            if(!Objects.equals(formPost.getNewTag(), "")) {
                postTags = createPostTags(formPost);
            }
        }

        if(!Objects.equals(category, formCategory))
            category = formCategory;

        dbPost.setTitle(formPost.getTitle());
        dbPost.setQuote(formPost.getQuote());
        dbPost.setTags(postTags);
        dbPost.setCategory(entityManager.merge(category));
        dbPost.setContent(formPost.getContent());
        dbPost.setStatus(PostStatus.PENDING.name());
        dbPost.setStatusMessage(PostStatusMessageConstant.PendingMessage);
        dbPost.setStatusMessageDetails(null);
        dbPost.setSlug(slug);
        entityManager.persist(dbPost);
        Post post = postRepository.saveAndFlush(dbPost);

        if(post.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    @Transactional
    public boolean createNewPost(PostDTO postDTO) {
        boolean isSaved = false;
        PersonDTO person = personService.getLoggedInUser();
        CategoryDTO categoryDTO = categoryService.findByCategoryId(postDTO.getFormCategory());
        Category category = entityMapperService.mapDTOToCategory(categoryDTO);
        String generatedSlug = slugUtil.createSlug(postDTO.getTitle());

        if(postRepository.existsBySlug(generatedSlug)) {
            createNewPost(postDTO);
        }

        if(!Objects.equals(postDTO.getNewTag(), "")) {
            String postTags = createPostTags(postDTO);
            postDTO.setTags(postTags);
        }

        postDTO.setStatus(PostStatus.PENDING.name());
        postDTO.setStatusMessage(PostStatusMessageConstant.PendingMessage);
        postDTO.setStatusMessageDetails(PostStatusMessageConstant.UploadImageMessage);
        postDTO.setSlug(generatedSlug);

        Post post = entityMapperService.mapDTOToPost(postDTO);
        post.setPerson(entityManager.merge(entityMapperService.mapDTOToPerson(person)));
        post.setCategory(entityManager.merge(category));
        entityManager.persist(post);
        post = postRepository.saveAndFlush(post);

        if(post.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    private String createPostTags(PostDTO post) {
        String postNewTag = stringUtil.processString(post.getNewTag());
        List<String> postTags = List.of(postNewTag.split(","));
        List<String> dbTags = tagService.getTagNames();
        List<String> nonExistingTags = stringUtil.findNonExistingIteminList(postTags, dbTags);

        for (String tag : nonExistingTags) {
            Tag newTag = new Tag();
            newTag.setName(tag);
            tagService.saveTag(newTag);
        }

        return post.getTags() + "," + postNewTag;
    }
}
