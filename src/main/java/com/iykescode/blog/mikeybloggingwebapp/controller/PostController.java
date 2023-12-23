package com.iykescode.blog.mikeybloggingwebapp.controller;

import com.iykescode.blog.mikeybloggingwebapp.constants.ImageDIRConstant;
import com.iykescode.blog.mikeybloggingwebapp.constants.PostStatus;
import com.iykescode.blog.mikeybloggingwebapp.constants.UrlPathConstant;
import com.iykescode.blog.mikeybloggingwebapp.constants.UrlTitleConstant;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostImageDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.TagDTO;
import com.iykescode.blog.mikeybloggingwebapp.service.*;
import com.iykescode.blog.mikeybloggingwebapp.validation.BasicFieldValidation;
import com.iykescode.blog.mikeybloggingwebapp.validation.BasicPostFieldValidation;
import com.iykescode.blog.mikeybloggingwebapp.validation.StatusMessageDetailsFieldValidation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class PostController {

    private final TagService tagService;
    private final PostService postService;
    private final CategoryService categoryService;
    private final PostImageService postImageService;
    private final PersonService personService;

    @GetMapping("/posts")
    public ModelAndView viewAllPosts(Model model,
                                     HttpServletRequest request,
                                     @RequestParam(value = "message", required = false) String message) {
        List<PostDTO> posts = postService.findByPostStatus(PostStatus.PUBLISHED.name());
        PersonDTO loggedInUser = personService.getLoggedInUser();
        postMainAttributes(model, request);
        messageParamAttributes(model, message);
        ModelAndView modelAndView = new ModelAndView("user/posts");
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("person", loggedInUser);
        return modelAndView;
    }

    @GetMapping("/posts/pending")
    public ModelAndView viewPendingPosts(Model model,
                                         HttpServletRequest request,
                                         @RequestParam(value = "message", required = false) String message) {
        List<PostDTO> posts = postService.findByPostStatus(PostStatus.PENDING.name());
        PersonDTO loggedInUser = personService.getLoggedInUser();
        postMainAttributes(model, request);
        messageParamAttributes(model, message);
        model.addAttribute("imagePath", UrlPathConstant.MainPath + ImageDIRConstant.postImages);
        model.addAttribute("post", new PostDTO());
        ModelAndView modelAndView = new ModelAndView("user/pending-posts");
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("person", loggedInUser);
        return modelAndView;
    }

    @GetMapping("/posts/drafts")
    public ModelAndView viewDraftPosts(Model model,
                                       HttpServletRequest request,
                                       @RequestParam(value = "message", required = false) String message) {
        List<PostDTO> posts = postService.findByPostStatus(PostStatus.DRAFT.name());
        PersonDTO loggedInUser = personService.getLoggedInUser();
        postMainAttributes(model, request);
        messageParamAttributes(model, message);
        model.addAttribute("imagePath", UrlPathConstant.MainPath + ImageDIRConstant.postImages);
        model.addAttribute("post", new PostDTO());
        ModelAndView modelAndView = new ModelAndView("user/draft-posts");
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("person", loggedInUser);
        return modelAndView;
    }

    @GetMapping("/posts/create")
    public String createPostPage(Model model, HttpServletRequest request) {
        postMainAttributes(model, request);
        model.addAttribute("post", new PostDTO());
        model.addAttribute("tags", tagService.findAllSortByAsc("name"));
        model.addAttribute("categories", categoryService.findAllSortByAsc("name"));
        return "user/post-form";
    }

    @GetMapping("/posts/upload-image/{slug}")
    public String createPostImagePage(@PathVariable String slug,
                                      Model model,
                                      HttpServletRequest request) {
        postMainAttributes(model, request);
        PostDTO byPostSlug = postService.findByPostSlug(slug);
        model.addAttribute("post", byPostSlug);
        model.addAttribute("image", new PostImageDTO());
        model.addAttribute("type", "post");
        return "user/image-form";
    }

    @GetMapping("/posts/update-image/{slug}")
    public String editPostImagePage(@PathVariable String slug,
                                      Model model,
                                      HttpServletRequest request) {
        postMainAttributes(model, request);
        PostDTO byPostSlug = postService.findByPostSlug(slug);
        model.addAttribute("post", byPostSlug);
        model.addAttribute("image", byPostSlug.getPostImage());
        model.addAttribute("imagePath", UrlPathConstant.MainPath + ImageDIRConstant.postImages);
        model.addAttribute("type", "post");
        return "user/image-form";
    }

    @GetMapping("/posts/edit/{slug}")
    public String editPost(@PathVariable String slug,
                           Model model,
                           HttpServletRequest request) {
        postMainAttributes(model, request);
        PostDTO byPostSlug = postService.findByPostSlug(slug);
        List<TagDTO> allSortByAsc = tagService.findAllSortByAsc("name");
        model.addAttribute("post", byPostSlug);
        model.addAttribute("tags", allSortByAsc);
        model.addAttribute("categories", categoryService.findAllSortByAsc("name"));
        return "user/post-form";
    }

    @PostMapping("/posts/create")
    public String createPost(@Validated(BasicPostFieldValidation .class) @ModelAttribute("post") PostDTO post,
                             Errors errors,
                             Model model, HttpServletRequest request) {
        postMainAttributes(model, request);
        model.addAttribute("tags", tagService.findAllSortByAsc("name"));
        model.addAttribute("categories", categoryService.findAllSortByAsc("name"));

        if(errors.hasErrors()) {
            return "user/post-form";
        }

        boolean isSaved = postService.createNewPost(post);
        if(isSaved)
            return "redirect:/user/posts/pending?message=created";

        return "redirect:/user/posts/pending?message=error";
    }

    @PostMapping("/posts/upload-image/{slug}")
    public String uploadPostImage(@PathVariable String slug,
                                  @Validated @ModelAttribute("image") PostImageDTO image,
                                  Errors errors,
                                  Model model,
                                  HttpServletRequest request) throws IOException {
        postMainAttributes(model, request);
        boolean isSaved;
        PostDTO post = postService.findByPostSlug(slug);
        PostImageDTO dbImage = post.getPostImage();
        model.addAttribute("post", post);
        model.addAttribute("type", "post");

        if(errors.hasErrors()) {
            return "user/image-form";
        }

        if(dbImage != null) {
            isSaved = postImageService.updateImage(image, dbImage);
        } else {
            isSaved = postImageService.createNewImage(post, image);
        }


        if(isSaved)
            return "redirect:/user/posts/pending?message=image-uploaded";

        return "redirect:/user/posts/pending?message=error";
    }

    @PatchMapping("/posts/update/{id}")
    public String updatePost(@PathVariable Integer id,
                             @Validated(BasicFieldValidation.class) @ModelAttribute("post") PostDTO post,
                             Errors errors,
                             Model model,
                             HttpServletRequest request) {
        postMainAttributes(model, request);
        model.addAttribute("tags", tagService.findAllSortByAsc("name"));
        model.addAttribute("categories", categoryService.findAllSortByAsc("name"));

        if(errors.hasErrors()) {
            return "user/post-form";
        }

        boolean isSaved = postService.updatePost(post, id);
        if(isSaved)
            return "redirect:/user/posts/pending?message=updated";

        return "redirect:/user/posts/pending?message=error";
    }

    @PatchMapping("/posts/approve/{id}")
    public String approvePost(@PathVariable Integer id) {
        PostDTO post = postService.findByPostId(id);
        boolean isSaved = postService.approvePost(post);

        if(!isSaved)
            return "redirect:/user/posts/pending?message=error";

        return "redirect:/user/posts/pending?message=approved";
    }

    @PatchMapping("/posts/draft/{id}")
    public String draftPost(@PathVariable Integer id) {
        PostDTO post = postService.findByPostId(id);
        boolean isSaved = postService.draftPost(post);

        if(!isSaved)
            return "redirect:/user/posts/pending?message=error";

        return "redirect:/user/posts?message=drafted";
    }

    @DeleteMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable Integer id) {
        postService.deleteByPostId(id);

        return "redirect:/user/posts/drafts?message=deleted";
    }

    @PatchMapping("/posts/disapprove/{id}")
    public String disapprovePost(@PathVariable Integer id,
                                 @Validated(StatusMessageDetailsFieldValidation.class) @ModelAttribute("post") PostDTO post,
                                 Errors errors,
                                 Model model,
                                 HttpServletRequest request) {
        postMainAttributes(model, request);
        List<PostDTO> posts = postService.findByPostStatus(PostStatus.PENDING.name());
        model.addAttribute("posts", posts);

        if(errors.hasErrors()) {
            return "user/pending-posts";
        }

        boolean isSaved = postService.disapprovePost(post, id);

        if(isSaved)
            return "redirect:/user/posts/pending?message=disapproved";

        return "redirect:/user/posts/pending?message=error";
    }

    @PatchMapping("/posts/republish/{id}")
    public String rePublishPost(@PathVariable Integer id) {
        PostDTO post = postService.findByPostId(id);
        boolean isSaved = postService.rePublishPost(post);

        if(!isSaved)
            return "redirect:/user/posts/drafts?message=error";

        return "redirect:/user/posts/pending?message=republished";
    }

    private void postMainAttributes(Model model, HttpServletRequest request) {
        model.addAttribute("title", UrlTitleConstant.AdminPosts);
        model.addAttribute("servletPath", request.getServletPath());
    }

    private void messageParamAttributes(Model model, String param) {
        if(!Objects.equals(param, null) && Objects.equals(param, "disapproved")){
            model.addAttribute("message", "Post has been disapproved successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "deleted")){
            model.addAttribute("message", "Post has been deleted successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "drafted")){
            model.addAttribute("message", "Post has been drafted successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "republished")){
            model.addAttribute("message", "Post has been submitted for Republish successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "approved")){
            model.addAttribute("message", "Post has been approved successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "updated")){
            model.addAttribute("message", "Post has been updated successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "created")){
            model.addAttribute("message", "Post has been created successfully !!");
            model.addAttribute("alertColor", "primary");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "image-uploaded")){
            model.addAttribute("message", "Image has been uploaded successfully !!");
            model.addAttribute("alertColor", "primary");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "error")){
            model.addAttribute("message", "There has been an error with your post !!");
            model.addAttribute("alertColor", "danger");
        }
    }
}
