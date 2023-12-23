package com.iykescode.blog.mikeybloggingwebapp.controller;

import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.RoleDTO;
import com.iykescode.blog.mikeybloggingwebapp.repository.PersonRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private PersonService personService;

    private PersonDTO formPersonDTO;

    @BeforeEach
    public void init() {
        RoleDTO roleDTO = RoleDTO.builder().id(1).roleName("ROLE_ADMIN").build();
        formPersonDTO = PersonDTO.builder().id(1).firstName("Michelle").lastName("Obama").email("michelleobama@usa.gov").username("michelle_obama").password("Admin123***").confirmPassword("Admin123***").role(roleDTO).build();
    }

    @Test
    public void userController_dashboard() throws Exception {
        mockMvc.perform(get("/user/"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/dashboard"));
    }

    @Test
    public void userController_viewAllUsers() throws Exception {
        mockMvc.perform(get("/user/users"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/users"));
    }

    @Test
    public void userController_createUserPage() throws Exception {
        mockMvc.perform(get("/user/users/create"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/user-form"));
    }

    @Test
    public void userController_editUser() throws Exception {
        when(personService.findByPersonId(ArgumentMatchers.any(Integer.class))).thenReturn(formPersonDTO);

        mockMvc.perform(get("/user/users/edit/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/user-form"));
    }

    @Test
    public void userController_createUser() throws Exception {
        given(personService.createNewPerson(ArgumentMatchers.any(PersonDTO.class))).willAnswer(invocationOnMock -> true);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("firstName", formPersonDTO.getFirstName());
        params.add("lastName", formPersonDTO.getLastName());
        params.add("email", formPersonDTO.getEmail());
        params.add("username", formPersonDTO.getUsername());
        params.add("password", formPersonDTO.getPassword());
        params.add("confirmPassword", formPersonDTO.getConfirmPassword());

        mockMvc.perform(post("/user/users/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/users?message=created"));
    }

    @Test
    public void userController_updateName() throws Exception {
        given(personService.updatePersonName(ArgumentMatchers.any(Integer.class), ArgumentMatchers.any(PersonDTO.class))).willAnswer(invocationOnMock -> true);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("firstName", formPersonDTO.getFirstName());
        params.add("lastName", formPersonDTO.getLastName());

        mockMvc.perform(patch("/user/users/update_name/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/users?message=updated"));
    }

    @Test
    public void userController_updateEmail() throws Exception {
        given(personService.updatePersonEmail(ArgumentMatchers.any(Integer.class), ArgumentMatchers.any(PersonDTO.class))).willAnswer(invocationOnMock -> true);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("email", formPersonDTO.getEmail());

        mockMvc.perform(patch("/user/users/update_email/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/users?message=updated"));
    }

    @Test
    public void userController_updateUsername() throws Exception {
        given(personService.updatePersonUsername(ArgumentMatchers.any(Integer.class), ArgumentMatchers.any(PersonDTO.class))).willAnswer(invocationOnMock -> true);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", formPersonDTO.getUsername());

        mockMvc.perform(patch("/user/users/update_username/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/users?message=updated"));
    }

    @Test
    public void userController_updatePassword() throws Exception {
        given(personService.updatePersonPassword(ArgumentMatchers.any(Integer.class), ArgumentMatchers.any(PersonDTO.class))).willAnswer(invocationOnMock -> true);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("password", formPersonDTO.getPassword());
        params.add("confirmPassword", formPersonDTO.getConfirmPassword());

        mockMvc.perform(patch("/user/users/update_password/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/users?message=updated"));
    }

    @Test
    public void userController_deleteUser() throws Exception {
        doNothing().when(personService).deleteByPersonId(ArgumentMatchers.any(Integer.class));

        mockMvc.perform(delete("/user/users/delete/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/users?message=deleted"));
    }
}
