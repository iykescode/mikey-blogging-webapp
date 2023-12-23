package com.iykescode.blog.mikeybloggingwebapp.controller;

import com.iykescode.blog.mikeybloggingwebapp.constants.UrlTitleConstant;
import com.iykescode.blog.mikeybloggingwebapp.dto.CategoryDTO;
import com.iykescode.blog.mikeybloggingwebapp.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ModelAndView viewAllCategories(Model model,
                                     HttpServletRequest request,
                                     @RequestParam(value = "message", required = false) String message) {
        List<CategoryDTO> categories = categoryService.findAllSortByAsc("name");
        postMainAttributes(model, request);
        messageParamAttributes(model, message);
        ModelAndView modelAndView = new ModelAndView("user/categories");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/categories/create")
    public String createCategoryPage(Model model, HttpServletRequest request) {
        postMainAttributes(model, request);
        model.addAttribute("category", new CategoryDTO());
        return "user/category-form";
    }

    @PostMapping("/categories/create")
    public String createCategory(@Validated @ModelAttribute("category") CategoryDTO category,
                             Errors errors,
                             Model model, HttpServletRequest request) {
        postMainAttributes(model, request);

        if(errors.hasErrors()) {
            return "user/category-form";
        }

        boolean isSaved = categoryService.createCategory(category);
        if(isSaved)
            return "redirect:/user/categories?message=created";

        return "redirect:/user/categories?message=error";
    }

    @DeleteMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.deleteByCategoryId(id);

        return "redirect:/user/categories?message=deleted";
    }

    private void postMainAttributes(Model model, HttpServletRequest request) {
        model.addAttribute("title", UrlTitleConstant.AdminCategories);
        model.addAttribute("servletPath", request.getServletPath());
    }

    private void messageParamAttributes(Model model, String param) {
        if(!Objects.equals(param, null) && Objects.equals(param, "deleted")){
            model.addAttribute("message", "Category has been deleted successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "updated")){
            model.addAttribute("message", "Category has been updated successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "created")){
            model.addAttribute("message", "Category has been created successfully !!");
            model.addAttribute("alertColor", "primary");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "error")){
            model.addAttribute("message", "There has been an error with your category !!");
            model.addAttribute("alertColor", "danger");
        }
    }
}
