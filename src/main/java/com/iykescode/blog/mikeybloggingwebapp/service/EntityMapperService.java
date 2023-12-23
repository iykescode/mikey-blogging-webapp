package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.*;
import com.iykescode.blog.mikeybloggingwebapp.model.*;

public interface EntityMapperService {

    // Category
    CategoryDTO mapCategoryToDTO(Category category);
    Category mapDTOToCategory(CategoryDTO categoryDTO);

    // Comment
    CommentDTO mapCommentToDTO(Comment comment);
    Comment mapDTOToComment(CommentDTO commentDTO);

    // Person
    PersonDTO mapPersonToDTO(Person person);
    Person mapDTOToPerson(PersonDTO personDTO);

    // PersonImage
    PersonImageDTO mapPersonImageToDTO(PersonImage personImage);
    PersonImage mapDTOToPersonImage(PersonImageDTO personImageDTO);

    // PersonProfile
    PersonProfileDTO mapPersonProfileToDTO(PersonProfile personProfile);
    PersonProfile mapDTOToPersonProfile(PersonProfileDTO personProfileDTO);

    // Post
    PostDTO mapPostToDTO(Post post);
    Post mapDTOToPost(PostDTO postDTO);

    // PostImage
    PostImageDTO mapPostImageToDTO(PostImage postImage);
    PostImage mapDTOToPostImage(PostImageDTO postImageDTO);

    // PostView
    PostViewDTO mapPostViewToDTO(PostView postView);
    PostView mapDTOToPostView(PostViewDTO postViewDTO);

    // Role
    RoleDTO mapRoleToDTO(Role role);
    Role mapDTOToRole(RoleDTO roleDTO);

    // Tag
    TagDTO mapTagToDTO(Tag tag);
    Tag mapDTOToTag(TagDTO tagDTO);
}
