package com.iykescode.blog.mikeybloggingwebapp.controller;

import com.iykescode.blog.mikeybloggingwebapp.constants.UrlTitleConstant;
import com.iykescode.blog.mikeybloggingwebapp.dto.CommentDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.service.CommentService;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserCommentController {

    private final CommentService commentService;
    private final PersonService personService;

    @GetMapping("/comments")
    public ModelAndView viewAllComments(Model model,
                                        HttpServletRequest request,
                                        @RequestParam(value = "message", required = false) String message) {
        List<CommentDTO> comments = commentService.findAllSortByDesc("createdAt");
        PersonDTO loggedInUser = personService.getLoggedInUser();
        postMainAttributes(model, request);
        messageParamAttributes(model, message);
        ModelAndView modelAndView = new ModelAndView("user/comments");
        modelAndView.addObject("comments", comments);
        modelAndView.addObject("person", loggedInUser);
        return modelAndView;
    }

    @DeleteMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable Integer id,
                                Model model,
                                HttpServletRequest request) {
        postMainAttributes(model, request);
        commentService.deleteByCommentId(id);

        return "redirect:/user/comments?message=deleted";
    }

    private void postMainAttributes(Model model, HttpServletRequest request) {
        model.addAttribute("title", UrlTitleConstant.AdminComments);
        model.addAttribute("servletPath", request.getServletPath());
    }

    private void messageParamAttributes(Model model, String param) {
        if(!Objects.equals(param, null) && Objects.equals(param, "deleted")){
            model.addAttribute("message", "Comment has been deleted successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "error")){
            model.addAttribute("message", "There has been an error with your comment !!");
            model.addAttribute("alertColor", "danger");
        }
    }
}
