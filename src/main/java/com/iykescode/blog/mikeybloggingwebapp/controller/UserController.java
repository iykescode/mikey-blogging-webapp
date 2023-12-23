package com.iykescode.blog.mikeybloggingwebapp.controller;

import com.iykescode.blog.mikeybloggingwebapp.constants.UrlTitleConstant;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonService;
import com.iykescode.blog.mikeybloggingwebapp.validation.BasicFieldValidation;
import com.iykescode.blog.mikeybloggingwebapp.validation.EmailFieldValidation;
import com.iykescode.blog.mikeybloggingwebapp.validation.PasswordFieldValidation;
import com.iykescode.blog.mikeybloggingwebapp.validation.UsernameFieldValidation;
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
public class UserController {

    private final PersonService personService;

    @GetMapping("/")
    public String dashboard(Model model, HttpServletRequest request) {
        model.addAttribute("title", UrlTitleConstant.AdminDashboard);
        model.addAttribute("servletPath", request.getServletPath());
        return "user/dashboard";
    }

    @GetMapping("/users")
    public ModelAndView viewAllUsers(Model model,
                                     HttpServletRequest request,
                                     @RequestParam(value = "message", required = false) String message) {
        List<PersonDTO> users = personService.findAll();
        userMainAttributes(model, request);
        messageParamAttributes(model, message);
        ModelAndView modelAndView = new ModelAndView("user/users");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/users/create")
    public String createUserPage(Model model, HttpServletRequest request) {
        userMainAttributes(model, request);
        model.addAttribute("person", new PersonDTO());
        return "user/user-form";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable Integer id, Model model, HttpServletRequest request) {
        userMainAttributes(model, request);
        model.addAttribute("person", personService.findByPersonId(id));
        return "user/user-form";
    }

    @PostMapping("/users/create")
    public String createUser(@Validated @ModelAttribute("person") PersonDTO person,
                             Errors errors,
                             Model model, HttpServletRequest request) {
        userMainAttributes(model, request);

        if(errors.hasErrors()) {
            return "user/user-form";
        }

        boolean isSaved = personService.createNewPerson(person);

        if(isSaved)
            return "redirect:/user/users?message=created";

        return "redirect:/user/users?message=error";
    }

    @PatchMapping("/users/update_name/{id}")
    public String updateName(@PathVariable Integer id,
                             @Validated(BasicFieldValidation.class) @ModelAttribute("person") PersonDTO person,
                             Errors errors,
                             Model model, HttpServletRequest request) {
        userMainAttributes(model, request);

        if(errors.hasErrors()) {
            return "user/user-form";
        }

        boolean isSaved = personService.updatePersonName(id, person);

        if(isSaved)
            return "redirect:/user/users?message=updated";

        return "redirect:/user/users?message=error";
    }

    @PatchMapping("/users/update_email/{id}")
    public String updateEmail(@PathVariable Integer id,
                             @Validated(EmailFieldValidation.class) @ModelAttribute("person") PersonDTO person,
                             Errors errors,
                             Model model, HttpServletRequest request) {
        userMainAttributes(model, request);

        if(errors.hasErrors()) {
            return "user/user-form";
        }

        boolean isSaved = personService.updatePersonEmail(id, person);

        if(isSaved)
            return "redirect:/user/users?message=updated";

        return "redirect:/user/users?message=error";
    }

    @PatchMapping("/users/update_username/{id}")
    public String updateUsername(@PathVariable Integer id,
                              @Validated(UsernameFieldValidation.class) @ModelAttribute("person") PersonDTO person,
                              Errors errors,
                              Model model, HttpServletRequest request) {
        userMainAttributes(model, request);

        if(errors.hasErrors()) {
            return "user/user-form";
        }

        boolean isSaved = personService.updatePersonUsername(id, person);

        if(isSaved)
            return "redirect:/user/users?message=updated";

        return "redirect:/user/users?message=error";
    }

    @PatchMapping("/users/update_password/{id}")
    public String updatePassword(@PathVariable Integer id,
                                 @Validated(PasswordFieldValidation.class) @ModelAttribute("person") PersonDTO person,
                                 Errors errors,
                                 Model model, HttpServletRequest request) {
        userMainAttributes(model, request);

        if(errors.hasErrors()) {
            return "user/user-form";
        }

        boolean isSaved = personService.updatePersonPassword(id, person);

        if(isSaved)
            return "redirect:/user/users?message=updated";

        return "redirect:/user/users?message=error";
    }

    @DeleteMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        personService.deleteByPersonId(id);

        return "redirect:/user/users?message=deleted";
    }

    private void userMainAttributes(Model model, HttpServletRequest request) {
        model.addAttribute("title", UrlTitleConstant.AdminUsers);
        model.addAttribute("servletPath", request.getServletPath());
    }

    private void messageParamAttributes(Model model, String param) {
        if(!Objects.equals(param, null) && Objects.equals(param, "created")){
            model.addAttribute("message", "User has been created successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "updated")){
            model.addAttribute("message", "User has been updated successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "deleted")){
            model.addAttribute("message", "User has been deleted successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "error")){
            model.addAttribute("message", "There was an error with User !!");
            model.addAttribute("alertColor", "danger");
        }
    }
}
