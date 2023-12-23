package com.iykescode.blog.mikeybloggingwebapp.service.impl;

import com.iykescode.blog.mikeybloggingwebapp.dto.*;
import com.iykescode.blog.mikeybloggingwebapp.model.*;
import com.iykescode.blog.mikeybloggingwebapp.service.EntityMapperService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class EntityMapperServiceImpl implements EntityMapperService {

    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO mapCategoryToDTO(Category category) {
        CategoryDTO categoryDTO = null;

        if (category != null)
            categoryDTO = modelMapper.map(category, CategoryDTO.class);

        return categoryDTO;
    }

    @Override
    public Category mapDTOToCategory(CategoryDTO categoryDTO) {
        Category category = null;

        if (categoryDTO != null)
            category = modelMapper.map(categoryDTO, Category.class);

        return category;
    }

    @Override
    public CommentDTO mapCommentToDTO(Comment comment) {
        CommentDTO commentDTO = null;

        if (comment != null) {
            commentDTO = modelMapper.map(comment, CommentDTO.class);

            if(!Objects.equals(comment.getPerson(), null)) {
                commentDTO.setPerson(mapPersonToDTO(comment.getPerson()));
            }

            if(!Objects.equals(comment.getPost(), null)) {
                commentDTO.setPost(mapPostToDTO(comment.getPost()));
            }
        }

        return commentDTO;
    }

    @Override
    public Comment mapDTOToComment(CommentDTO commentDTO) {
        Comment comment = null;

        if (commentDTO != null) {
            comment = modelMapper.map(commentDTO, Comment.class);

            if (!Objects.equals(commentDTO.getPerson(), null)) {
                comment.setPerson(mapDTOToPerson(commentDTO.getPerson()));
            }

            if (!Objects.equals(commentDTO.getPost(), null)) {
                comment.setPost(mapDTOToPost(commentDTO.getPost()));
            }
        }

        return comment;
    }

    @Override
    public PersonDTO mapPersonToDTO(Person person) {
        PersonDTO personDTO = null;

        if(person !=  null) {
            personDTO = modelMapper.map(person, PersonDTO.class);

            if(!Objects.equals(person.getRole(), null)) {
                personDTO.setRole(mapRoleToDTO(person.getRole()));
            }

            if(!Objects.equals(person.getPersonImage(), null)) {
                personDTO.setPersonImage(mapPersonImageToDTO(person.getPersonImage()));
            }

            if(!Objects.equals(person.getPersonProfile(), null)) {
                personDTO.setPersonProfile(mapPersonProfileToDTO(person.getPersonProfile()));
            }
        }

        return personDTO;
    }

    @Override
    public Person mapDTOToPerson(PersonDTO personDTO) {
        Person person = null;

        if(personDTO != null) {
            person = modelMapper.map(personDTO, Person.class);

            if(!Objects.equals(personDTO.getRole(), null)) {
                person.setRole(mapDTOToRole(personDTO.getRole()));
            }

            if(!Objects.equals(personDTO.getPersonImage(), null)) {
                person.setPersonImage(mapDTOToPersonImage(personDTO.getPersonImage()));
            }

            if(!Objects.equals(personDTO.getPersonProfile(), null)) {
                person.setPersonProfile(mapDTOToPersonProfile(personDTO.getPersonProfile()));
            }
        }

        return person;
    }

    @Override
    public PersonImageDTO mapPersonImageToDTO(PersonImage personImage) {
        PersonImageDTO personImageDTO = null;

        if (personImage != null)
            personImageDTO = modelMapper.map(personImage, PersonImageDTO.class);

        return personImageDTO;
    }

    @Override
    public PersonImage mapDTOToPersonImage(PersonImageDTO personImageDTO) {
        PersonImage personImage = null;

        if (personImageDTO != null)
            personImage = modelMapper.map(personImageDTO, PersonImage.class);

        return personImage;
    }

    @Override
    public PersonProfileDTO mapPersonProfileToDTO(PersonProfile personProfile) {
        PersonProfileDTO personProfileDTO = null;

        if (personProfile != null)
            personProfileDTO = modelMapper.map(personProfile, PersonProfileDTO.class);

        return personProfileDTO;
    }

    @Override
    public PersonProfile mapDTOToPersonProfile(PersonProfileDTO personProfileDTO) {
        PersonProfile personProfile = null;

        if (personProfileDTO != null)
            personProfile = modelMapper.map(personProfileDTO, PersonProfile.class);

        return personProfile;
    }

    @Override
    public PostDTO mapPostToDTO(Post post) {
        PostDTO postDTO = null;

        if (post != null) {
            postDTO = modelMapper.map(post, PostDTO.class);

            if (!Objects.equals(post.getCategory(), null)) {
                postDTO.setCategory(mapCategoryToDTO(post.getCategory()));
            }

            if (!Objects.equals(post.getPerson(), null)) {
                postDTO.setPerson(mapPersonToDTO(post.getPerson()));
            }

            if (!Objects.equals(post.getPostImage(), null)) {
                postDTO.setPostImage(mapPostImageToDTO(post.getPostImage()));
            }
        }

        return postDTO;
    }

    @Override
    public Post mapDTOToPost(PostDTO postDTO) {
        Post post = null;

        if (postDTO != null) {
            post = modelMapper.map(postDTO, Post.class);

            if (!Objects.equals(postDTO.getCategory(), null)) {
                post.setCategory(mapDTOToCategory(postDTO.getCategory()));
            }

            if (!Objects.equals(postDTO.getPerson(), null)) {
                post.setPerson(mapDTOToPerson(postDTO.getPerson()));
            }

            if (!Objects.equals(postDTO.getPostImage(), null)) {
                post.setPostImage(mapDTOToPostImage(postDTO.getPostImage()));
            }
        }

        return post;
    }

    @Override
    public PostImageDTO mapPostImageToDTO(PostImage postImage) {
        PostImageDTO postImageDTO = null;

        if (postImage != null)
            postImageDTO = modelMapper.map(postImage, PostImageDTO.class);

        return postImageDTO;
    }

    @Override
    public PostImage mapDTOToPostImage(PostImageDTO postImageDTO) {
        PostImage postImage= null;

        if (postImageDTO != null)
            postImage = modelMapper.map(postImageDTO, PostImage.class);

        return postImage;
    }

    @Override
    public PostViewDTO mapPostViewToDTO(PostView postView) {
        PostViewDTO postViewDTO = null;

        if (postView != null) {
            postViewDTO = modelMapper.map(postView, PostViewDTO.class);

            if (!Objects.equals(postView.getPerson(), null)) {
                postViewDTO.setPerson(mapPersonToDTO(postView.getPerson()));
            }

            if (!Objects.equals(postView.getPost(), null)) {
                postViewDTO.setPost(mapPostToDTO(postView.getPost()));
            }
        }

        return postViewDTO;
    }

    @Override
    public PostView mapDTOToPostView(PostViewDTO postViewDTO) {
        PostView postView = null;

        if (postViewDTO != null) {
            postView = modelMapper.map(postViewDTO, PostView.class);

            if (!Objects.equals(postViewDTO.getPerson(), null)) {
                postView.setPerson(mapDTOToPerson(postViewDTO.getPerson()));
            }

            if (!Objects.equals(postViewDTO.getPost(), null)) {
                postView.setPost(mapDTOToPost(postViewDTO.getPost()));
            }
        }

        return postView;
    }

    @Override
    public RoleDTO mapRoleToDTO(Role role) {
        RoleDTO roleDTO = null;

        if (role != null)
            roleDTO = modelMapper.map(role, RoleDTO.class);

        return roleDTO;
    }

    @Override
    public Role mapDTOToRole(RoleDTO roleDTO) {
        Role role = null;

        if (roleDTO != null)
            role = modelMapper.map(roleDTO, Role.class);

        return role;
    }

    @Override
    public TagDTO mapTagToDTO(Tag tag) {
        TagDTO tagDTO = null;

        if (tag != null)
            tagDTO = modelMapper.map(tag, TagDTO.class);

        return tagDTO;
    }

    @Override
    public Tag mapDTOToTag(TagDTO tagDTO) {
        Tag tag = null;

        if (tagDTO != null)
            tag = modelMapper.map(tagDTO, Tag.class);

        return tag;
    }
}
