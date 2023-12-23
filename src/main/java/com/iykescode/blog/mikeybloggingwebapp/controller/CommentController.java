package com.iykescode.blog.mikeybloggingwebapp.controller;

import com.iykescode.blog.mikeybloggingwebapp.constants.ImageDIRConstant;
import com.iykescode.blog.mikeybloggingwebapp.constants.UrlPathConstant;
import com.iykescode.blog.mikeybloggingwebapp.constants.UrlTitleConstant;
import com.iykescode.blog.mikeybloggingwebapp.dto.CommentDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostDTO;
import com.iykescode.blog.mikeybloggingwebapp.service.CommentService;
import com.iykescode.blog.mikeybloggingwebapp.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/blog")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/add-comment/{slug}")
    public String createComment(@PathVariable String slug,
                                @Validated @ModelAttribute("comment") CommentDTO comment,
                                Errors errors,
                                Model model,
                                HttpServletRequest request) {
        PostDTO post = postService.findByPostSlug(slug);
        model.addAttribute("title", UrlTitleConstant.BlogSingle);
        model.addAttribute("servletPath", request.getServletPath());
        model.addAttribute("imagePath", UrlPathConstant.MainPath + ImageDIRConstant.postImages);
        model.addAttribute("post", post);

        if(errors.hasErrors()) {
            return "blog/blog-single";
        }

        boolean isSaved = commentService.createComment(comment, post);

        if(isSaved)
            return String.format("redirect:/blog/view/%s?add-comment=true", slug);

        return String.format("redirect:/blog/view/%s?add-comment=error", slug);
    }

    @DeleteMapping("/{slug}/delete-comment/{id}")
    public String deleteComment(@PathVariable String slug,
                                @PathVariable Integer id,
                                Model model,
                                HttpServletRequest request) {
        model.addAttribute("title", UrlTitleConstant.BlogSingle);
        model.addAttribute("servletPath", request.getServletPath());
        commentService.deleteByCommentId(id);

        return String.format("redirect:/blog/view/%s?delete-comment=true", slug);
    }
}
