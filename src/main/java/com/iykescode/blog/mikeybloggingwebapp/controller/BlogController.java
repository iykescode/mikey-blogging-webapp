package com.iykescode.blog.mikeybloggingwebapp.controller;

import com.iykescode.blog.mikeybloggingwebapp.constants.ImageDIRConstant;
import com.iykescode.blog.mikeybloggingwebapp.constants.PostStatus;
import com.iykescode.blog.mikeybloggingwebapp.constants.UrlPathConstant;
import com.iykescode.blog.mikeybloggingwebapp.constants.UrlTitleConstant;
import com.iykescode.blog.mikeybloggingwebapp.dto.CommentDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostViewDTO;
import com.iykescode.blog.mikeybloggingwebapp.service.CommentService;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonService;
import com.iykescode.blog.mikeybloggingwebapp.service.PostService;
import com.iykescode.blog.mikeybloggingwebapp.service.PostViewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/blog")
public class BlogController {

    private final PostService postService;
    private final PersonService personService;
    private final PostViewService postViewService;
    private final CommentService commentService;

    @GetMapping("/blog-with-sidebar")
    public ModelAndView BlogWithSideBar(Model model,
                                        HttpServletRequest request,
                                        @RequestParam(defaultValue = "1") int pageNo,
                                        @RequestParam(defaultValue = "10") int pageSize,
                                        @RequestParam(defaultValue = "title") String sortBy) {
        model.addAttribute("title", UrlTitleConstant.BlogWithSideBar);
        model.addAttribute("servletPath", request.getServletPath());
        model.addAttribute("imagePath", UrlPathConstant.MainPath + ImageDIRConstant.postImages);
        model.addAttribute("personImagePath", UrlPathConstant.MainPath + ImageDIRConstant.userImages);
        Page<PostDTO> page = postService.getPaginatedAndSortedProducts(pageNo, pageSize, sortBy);
        return elementsForBlogWithSideBar(page, pageNo, pageSize, sortBy);
    }

    @GetMapping("/search/{keyword}")
    public ModelAndView BlogWithSideBarBySearch(@PathVariable String keyword,
                                                Model model,
                                                HttpServletRequest request,
                                                @RequestParam(defaultValue = "1") int pageNo,
                                                @RequestParam(defaultValue = "10") int pageSize,
                                                @RequestParam(defaultValue = "title") String sortBy) {
        model.addAttribute("title", UrlTitleConstant.BlogWithSideBar);
        model.addAttribute("servletPath", request.getServletPath());
        model.addAttribute("imagePath", UrlPathConstant.MainPath + ImageDIRConstant.postImages);
        model.addAttribute("personImagePath", UrlPathConstant.MainPath + ImageDIRConstant.userImages);
        Page<PostDTO> page = postService.getPaginatedAndSortedProductsBySearch(pageNo, pageSize, sortBy, keyword);
        return elementsForBlogWithSideBar(page, pageNo, pageSize, sortBy);
    }

    @PostMapping("/search")
    public String SearchBlog(@RequestParam String keyword,
                             Model model,
                             HttpServletRequest request) {
        model.addAttribute("title", UrlTitleConstant.BlogWithSideBar);
        model.addAttribute("servletPath", request.getServletPath());

        boolean isSaved = postService.existsByPostTag(keyword);

        return isSaved ? "redirect:/blog/search/"+keyword : "redirect:/blog/blog-with-sidebar";
    }

    @GetMapping("/view/{slug}")
    public ModelAndView BlogSingle(@PathVariable String slug, Model model, HttpServletRequest request) {
        PostDTO post = postService.findByPostSlug(slug);
        List<PostDTO> allPosts = postService.findByPostStatus(PostStatus.PUBLISHED.name());
        List<PostViewDTO> postViews = postViewService.findByPost(post);
        List<CommentDTO> comments = commentService.findCommentByPost(post);
        PersonDTO person = personService.getLoggedInUser();
        Page<PostDTO> latestPosts = postService.getLatestPosts();
        PostDTO nextPost = null;
        PostDTO previousPost = null;

        if(person != null && !Objects.equals(person.getId(), post.getPerson().getId()))
            postViewService.createPostView(post, person);

        // Loop for deciding previous and next post based on current post
        for (int i=0; i<allPosts.size(); i++) {
            PostDTO loopPost = allPosts.get(i);

            if(loopPost.getId().equals(post.getId())) {
                if(i>0) {
                    previousPost = allPosts.get(i-1);
                }

                if(i< allPosts.size()-1) {
                    nextPost = allPosts.get(i+1);
                }
            }
        }

        model.addAttribute("title", UrlTitleConstant.BlogSingle);
        model.addAttribute("servletPath", request.getServletPath());
        model.addAttribute("imagePath", UrlPathConstant.MainPath + ImageDIRConstant.postImages);
        model.addAttribute("personImagePath", UrlPathConstant.MainPath + ImageDIRConstant.userImages);
        model.addAttribute("comment", new CommentDTO());
        ModelAndView modelAndView = new ModelAndView("blog/blog-single");

        if(!Objects.equals(post.getStatus(), PostStatus.PUBLISHED.name())){
            post = postService.findByPostSlug("This is just a simple error trigger if post is not with a Published status - odhlfjfghgdgjkhgfkjdfgijfhdghjgjkfgsvsfgghg");
        }

        modelAndView.addObject("post", post);
        modelAndView.addObject("nextPost", nextPost);
        modelAndView.addObject("previousPost", previousPost);
        modelAndView.addObject("comments", comments);
        modelAndView.addObject("postViews", (long) postViews.size());
        modelAndView.addObject("commentsCount", (long) comments.size());
        modelAndView.addObject("postTags", List.of(post.getTags().split(",")));
        modelAndView.addObject("latestPosts", latestPosts);
        modelAndView.addObject("loggedInUser", person);
        return modelAndView;
    }

    private ModelAndView elementsForBlogWithSideBar(Page<PostDTO> page, Integer pageNo, Integer pageSize, String sortBy) {
        List<PostDTO> posts = page.getContent();
        PersonDTO loggedInUser = personService.getLoggedInUser();
        Page<PostDTO> latestPosts = postService.getLatestPosts();

        ModelAndView modelAndView = new ModelAndView("blog/blog-with-sidebar");
        modelAndView.addObject("currentPage", pageNo);
        modelAndView.addObject("totalPages", page.getTotalPages());
        modelAndView.addObject("totalItems", page.getTotalElements());
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("sortBy", sortBy);
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("person", loggedInUser);
        modelAndView.addObject("latestPosts", latestPosts);
        return modelAndView;
    }
}
