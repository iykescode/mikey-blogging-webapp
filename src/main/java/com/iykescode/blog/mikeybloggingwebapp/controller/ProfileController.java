package com.iykescode.blog.mikeybloggingwebapp.controller;

import com.iykescode.blog.mikeybloggingwebapp.constants.ImageDIRConstant;
import com.iykescode.blog.mikeybloggingwebapp.constants.UrlPathConstant;
import com.iykescode.blog.mikeybloggingwebapp.constants.UrlTitleConstant;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonImageDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonProfileDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PostImageDTO;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonImageService;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonProfileService;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class ProfileController {

    private final PersonService personService;
    private final PersonImageService personImageService;
    private final PersonProfileService personProfileService;

    @GetMapping("/profile")
    public ModelAndView viewProfile(Model model,
                                    HttpServletRequest request,
                                    @RequestParam(value = "message", required = false) String message) {
        profileMainAttributes(model, request);
        messageParamAttributes(model, message);
        PersonDTO person = personService.getLoggedInUser();
        PersonProfileDTO personProfile = person.getPersonProfile();
        ModelAndView modelAndView = new ModelAndView("user/user-profile");
        model.addAttribute("imagePath", UrlPathConstant.MainPath + ImageDIRConstant.userImages);
        modelAndView.addObject("person", person);
        modelAndView.addObject("profile", Objects.requireNonNullElseGet(personProfile, PersonProfileDTO::new));

        if(person.getPersonImage() != null) {
            model.addAttribute("personImageCheck", "true");
        } else {
            model.addAttribute("personImageCheck", "false");
        }

        return modelAndView;
    }

    @PostMapping("/profile/update/{social}")
    public String updateSocial(@PathVariable String social,
                                 @Validated @ModelAttribute("profile") PersonProfileDTO profile,
                                 Errors errors,
                                 Model model,
                                 HttpServletRequest request) {
        profileMainAttributes(model, request);
        PersonDTO person = personService.getLoggedInUser();
        model.addAttribute("person", person);
        boolean isSaved;

        if(errors.hasErrors()) {
            return "user/user-profile";
        }

        if (person.getPersonProfile() != null) {
            isSaved = switch (social) {
                case "facebook" -> personProfileService.updateFacebook(person.getPersonProfile(), profile);
                case "twitter" -> personProfileService.updateTwitter(person.getPersonProfile(), profile);
                case "instagram" -> personProfileService.updateInstagram(person.getPersonProfile(), profile);
                case "linkedIn" -> personProfileService.updateLinkedIn(person.getPersonProfile(), profile);
                case "headline" -> personProfileService.updateHeadline(person.getPersonProfile(), profile);
                case "summary" -> personProfileService.updateSummary(person.getPersonProfile(), profile);
                default -> false;
            };
        } else {
            isSaved = personProfileService.createProfile(person, profile);
        }

        if(isSaved)
            return String.format("redirect:/user/profile?message=%s-updated", social);

        return "redirect:/user/profile?message=error";
    }

    @GetMapping("/profile/upload-image")
    public String createPersonImagePage(Model model, HttpServletRequest request) {
        profileMainAttributes(model, request);
        PersonDTO loggedInUser = personService.getLoggedInUser();
        model.addAttribute("person", loggedInUser);
        model.addAttribute("image", new PostImageDTO());
        model.addAttribute("type", "person");
        return "user/image-form";
    }

    @GetMapping("/profile/update-image")
    public String editPersonImagePage(Model model, HttpServletRequest request) {
        profileMainAttributes(model, request);
        PersonDTO loggedInUser = personService.getLoggedInUser();
        model.addAttribute("person", loggedInUser);
        model.addAttribute("image", loggedInUser.getPersonImage());
        model.addAttribute("imagePath", UrlPathConstant.MainPath + ImageDIRConstant.userImages);
        model.addAttribute("type", "person");
        return "user/image-form";
    }

    @PostMapping("/profile/upload-image")
    public String uploadPostImage(@Validated @ModelAttribute("image") PersonImageDTO image,
                                  Errors errors,
                                  Model model,
                                  HttpServletRequest request) throws IOException {
        boolean isSaved;
        PersonDTO person = personService.getLoggedInUser();
        PersonImageDTO dbImage = person.getPersonImage();
        profileMainAttributes(model, request);
        model.addAttribute("person", person);
        model.addAttribute("type", "person");

        if(errors.hasErrors()) {
            return "user/image-form";
        }

        if(dbImage != null) {
            isSaved = personImageService.updateImage(image, dbImage);
        } else {
            isSaved = personImageService.createNewImage(person, image);
        }


        if(isSaved)
            return "redirect:/user/profile?message=image-uploaded";

        return "redirect:/user/profile?message-error";
    }

    private void profileMainAttributes(Model model, HttpServletRequest request) {
        model.addAttribute("title", UrlTitleConstant.AdminProfile);
        model.addAttribute("servletPath", request.getServletPath());
    }

    private void messageParamAttributes(Model model, String param) {
        if(!Objects.equals(param, null) && Objects.equals(param, "headline-updated")){
            model.addAttribute("message", "Headline has been updated successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "summary-updated")){
            model.addAttribute("message", "Summary has been updated successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "facebook-updated")){
            model.addAttribute("message", "Facebook has been updated successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "twitter-updated")){
            model.addAttribute("message", "Twitter has been updated successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "instagram-updated")){
            model.addAttribute("message", "Instagram has been updated successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "linkedIn-updated")){
            model.addAttribute("message", "LinkedIn has been updated successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "image-uploaded")){
            model.addAttribute("message", "Image has been uploaded successfully !!");
            model.addAttribute("alertColor", "success");
        } else if(!Objects.equals(param, null) && Objects.equals(param, "error")){
            model.addAttribute("message", "There was an error with updating User !!");
            model.addAttribute("alertColor", "danger");
        }
    }
}
