package com.iykescode.blog.mikeybloggingwebapp.controller;

import com.iykescode.blog.mikeybloggingwebapp.constants.UrlPathConstant;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonImageDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonProfileDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.RoleDTO;
import com.iykescode.blog.mikeybloggingwebapp.repository.PersonImageRepository;
import com.iykescode.blog.mikeybloggingwebapp.repository.PersonProfileRepository;
import com.iykescode.blog.mikeybloggingwebapp.repository.PersonRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonImageService;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonProfileService;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PersonService personService;

    @MockBean
    private PersonImageRepository personImageRepository;

    @MockBean
    private PersonImageService personImageService;

    @MockBean
    private PersonProfileRepository personProfileRepository;

    @MockBean
    private PersonProfileService personProfileService;

    @MockBean
    private UrlPathConstant urlPathConstant;

    private PersonDTO personDTO;
    private final PersonProfileDTO formPersonProfile = new PersonProfileDTO();

    @BeforeEach
    public void init() {
        RoleDTO roleDTO = RoleDTO.builder()
                .id(1)
                .roleName("ROLE_ADMIN")
                .build();
        PersonImageDTO personImageDTO = PersonImageDTO.builder()
                .id(1)
                .image("image.jpg")
                .build();
        PersonProfileDTO personProfileDTO = PersonProfileDTO.builder()
                .id(1)
                .headline("This is a simple headline")
                .summary("This is a simple summary")
                .facebook("https://www.facebook.com")
                .twitter("https://www.twitter.com")
                .instagram("https://www.instagram.com")
                .linkedIn("https://www.linkedin.com")
                .build();
        personDTO = PersonDTO.builder()
                .id(1)
                .firstName("Michael")
                .lastName("Jordan")
                .email("michaeljordan@yahoo.com")
                .username("michael_jordan")
                .password("admin")
                .role(roleDTO)
                .personProfile(personProfileDTO)
                .personImage(personImageDTO)
                .build();
    }

    @Test
    public void profileController_viewProfile() throws Exception {
        given(personService.getLoggedInUser()).willAnswer(invocationOnMock -> personDTO);

        mockMvc.perform(get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/user-profile"));
    }

    @Test
    public void profileController_updateSocial() throws Exception {
        String social;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        given(personService.getLoggedInUser()).willAnswer(invocationOnMock -> personDTO);
        given(personProfileService.updateHeadline(ArgumentMatchers.any(PersonProfileDTO.class), ArgumentMatchers.any(PersonProfileDTO.class))).willAnswer(invocationOnMock -> true);
        given(personProfileService.updateSummary(ArgumentMatchers.any(PersonProfileDTO.class), ArgumentMatchers.any(PersonProfileDTO.class))).willAnswer(invocationOnMock -> true);
        given(personProfileService.updateFacebook(ArgumentMatchers.any(PersonProfileDTO.class), ArgumentMatchers.any(PersonProfileDTO.class))).willAnswer(invocationOnMock -> true);
        given(personProfileService.updateTwitter(ArgumentMatchers.any(PersonProfileDTO.class), ArgumentMatchers.any(PersonProfileDTO.class))).willAnswer(invocationOnMock -> true);
        given(personProfileService.updateInstagram(ArgumentMatchers.any(PersonProfileDTO.class), ArgumentMatchers.any(PersonProfileDTO.class))).willAnswer(invocationOnMock -> true);
        given(personProfileService.updateLinkedIn(ArgumentMatchers.any(PersonProfileDTO.class), ArgumentMatchers.any(PersonProfileDTO.class))).willAnswer(invocationOnMock -> true);

        // Testing Headline
        social = "headline";
        formPersonProfile.setHeadline("Facebook Manager - Operations Managerial Commissioner");
        params.add(social, formPersonProfile.getHeadline());

        mockMvc.perform(post(String.format("/user/profile/update/%s", social))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/user/profile?message=%s-updated", social)));

        // Testing Summary
        social = "summary";
        formPersonProfile.setSummary("Facebook Manager - Operations Managerial Commissioner - This is a quick summary");
        params.add(social, formPersonProfile.getSummary());

        mockMvc.perform(post(String.format("/user/profile/update/%s", social))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/user/profile?message=%s-updated", social)));

        // Testing Facebook
        social = "facebook";
        formPersonProfile.setFacebook("https://www.facebook.com");
        params.add(social, formPersonProfile.getFacebook());

        mockMvc.perform(post(String.format("/user/profile/update/%s", social))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/user/profile?message=%s-updated", social)));

        // Testing Twitter
        social = "twitter";
        formPersonProfile.setTwitter("https://www.twitter.com");
        params.add(social, formPersonProfile.getTwitter());

        mockMvc.perform(post(String.format("/user/profile/update/%s", social))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/user/profile?message=%s-updated", social)));

        // Testing Instagram
        social = "instagram";
        formPersonProfile.setInstagram("https://www.instagram.com");
        params.add(social, formPersonProfile.getInstagram());

        mockMvc.perform(post(String.format("/user/profile/update/%s", social))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/user/profile?message=%s-updated", social)));

        // Testing LinkedIn
        social = "linkedIn";
        formPersonProfile.setLinkedIn("https://www.linkedIn.com");
        params.add(social, formPersonProfile.getLinkedIn());

        mockMvc.perform(post(String.format("/user/profile/update/%s", social))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(String.format("/user/profile?message=%s-updated", social)));
    }

    @Test
    public void profileController_createPersonImagePage() throws Exception {
        given(personService.getLoggedInUser()).willAnswer(invocationOnMock -> personDTO);

        mockMvc.perform(get("/user/profile/upload-image"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/image-form"));
    }

    @Test
    public void profileController_editPersonImagePage() throws Exception {
        given(personService.getLoggedInUser()).willAnswer(invocationOnMock -> personDTO);

        mockMvc.perform(get("/user/profile/update-image"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/image-form"));
    }

    @Test
    public void profileController_uploadPostImage() throws Exception {
        given(personService.getLoggedInUser()).willAnswer(invocationOnMock -> personDTO);
        given(personImageService.updateImage(ArgumentMatchers.any(PersonImageDTO.class), ArgumentMatchers.any(PersonImageDTO.class))).willAnswer(invocationOnMock -> true);
        given(personImageService.createNewImage(ArgumentMatchers.any(PersonDTO.class), ArgumentMatchers.any(PersonImageDTO.class))).willAnswer(invocationOnMock -> true);

        MultipartFile file = Mockito.mock(MultipartFile.class);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("imageContent", "filename.png", "image/png", file.getBytes());

        mockMvc.perform(multipart("/user/profile/upload-image")
                        .file(mockMultipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/profile?message=image-uploaded"));
    }
}
