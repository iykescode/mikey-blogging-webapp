package com.iykescode.blog.mikeybloggingwebapp.service.impl;

import com.iykescode.blog.mikeybloggingwebapp.dto.CommentDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Comment;
import com.iykescode.blog.mikeybloggingwebapp.model.Post;
import com.iykescode.blog.mikeybloggingwebapp.repository.CommentRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.CommentService;
import com.iykescode.blog.mikeybloggingwebapp.service.EntityMapperService;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EntityMapperService entityMapperService;
    private final PersonService personService;
    private final EntityManager entityManager;

    @Override
    public List<CommentDTO> findAllSortByDesc(String attribute) {
        Sort sort = Sort.by(Sort.Direction.DESC, attribute);
        List<Comment> commentList = commentRepository.findAll(sort);
        return commentList.stream()
                .map(entityMapperService::mapCommentToDTO)
                .toList();
    }

    @Override
    public void deleteByCommentId(Integer id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        commentRepository.delete(comment);
    }

    @Override
    public CommentDTO findByCommentId(Integer id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        return entityMapperService.mapCommentToDTO(comment);
    }

    @Override
    public List<CommentDTO> findCommentByPost(PostDTO postDTO) {
        List<Comment> comments = commentRepository.findByPost(entityMapperService.mapDTOToPost(postDTO));
        return comments.stream()
                .map(entityMapperService::mapCommentToDTO)
                .toList();
    }

    @Override
    @Transactional
    public boolean createComment(CommentDTO commentDTO, PostDTO postDTO) {
        boolean isSaved = false;
        PersonDTO person = personService.getLoggedInUser();

        Comment comment = entityMapperService.mapDTOToComment(commentDTO);
        Post post = entityMapperService.mapDTOToPost(postDTO);

        comment.setPost(entityManager.merge(post));
        comment.setPerson(entityManager.merge(entityMapperService.mapDTOToPerson(person)));
        entityManager.persist(comment);
        Comment savedComment = commentRepository.saveAndFlush(comment);

        if(savedComment.getId() > 0)
            isSaved = true;

        return isSaved;
    }
}
