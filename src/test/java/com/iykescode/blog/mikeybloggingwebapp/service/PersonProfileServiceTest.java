package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonProfileDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.RoleDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Person;
import com.iykescode.blog.mikeybloggingwebapp.model.PersonProfile;
import com.iykescode.blog.mikeybloggingwebapp.model.Role;
import com.iykescode.blog.mikeybloggingwebapp.repository.PersonProfileRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.impl.PersonProfileServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonProfileServiceTest {

    @Mock
    private PersonProfileRepository personProfileRepository;
    @Mock
    private PersonService personService;
    @Mock
    private EntityMapperService entityMapperService;

    @InjectMocks
    private PersonProfileServiceImpl personProfileService;

    private Person person;
    private PersonDTO personDTO;
    private final PersonProfileDTO formPersonProfile = new PersonProfileDTO();
    private PersonProfile personProfile;
    private PersonProfileDTO personProfileDTO;

    @BeforeEach
    public void init() {
        Role role = Role.builder()
                .id(1)
                .roleName("ROLE_ADMIN")
                .build();
        RoleDTO roleDTO = RoleDTO.builder()
                .id(1)
                .roleName("ROLE_ADMIN")
                .build();
        personProfile = PersonProfile.builder()
                .id(1)
                .headline("This is a simple headline")
                .summary("This is a simple summary")
                .facebook("https://www.facebook.com")
                .twitter("https://www.twitter.com")
                .instagram("https://www.instagram.com")
                .linkedIn("https://www.linkedin.com")
                .build();
        personProfileDTO = PersonProfileDTO.builder()
                .id(1)
                .headline("This is a simple headline")
                .summary("This is a simple summary")
                .facebook("https://www.facebook.com")
                .twitter("https://www.twitter.com")
                .instagram("https://www.instagram.com")
                .linkedIn("https://www.linkedin.com")
                .build();
        person = Person.builder()
                .id(1)
                .firstName("Michael")
                .lastName("Jordan")
                .email("michaeljordan@yahoo.com")
                .username("michael_jordan")
                .password("admin")
                .role(role)
                .personProfile(personProfile)
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
                .build();
    }

    @Test
    public void personProfileService_createProfile() {
        when(entityMapperService.mapDTOToPersonProfile(Mockito.any(PersonProfileDTO.class))).thenReturn(personProfile);
        when(entityMapperService.mapDTOToPerson(Mockito.any(PersonDTO.class))).thenReturn(person);
        when(entityMapperService.mapPersonToDTO(Mockito.any(Person.class))).thenReturn(personDTO);
        when(personProfileRepository.saveAndFlush(Mockito.any(PersonProfile.class))).thenReturn(personProfile);
        when(personService.savePerson(Mockito.any(PersonDTO.class))).thenReturn(personDTO);

        boolean isSaved = personProfileService.createProfile(personDTO, personProfileDTO);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void personProfileService_updateHeadline() {
        formPersonProfile.setHeadline("This is my form headline");

        when(entityMapperService.mapDTOToPersonProfile(Mockito.any(PersonProfileDTO.class))).thenReturn(personProfile);
        when(personProfileRepository.saveAndFlush(Mockito.any(PersonProfile.class))).thenReturn(personProfile);

        boolean isSaved = personProfileService.updateHeadline(personProfileDTO, formPersonProfile);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void personProfileService_updateSummary() {
        formPersonProfile.setSummary("This is my form summary");

        when(entityMapperService.mapDTOToPersonProfile(Mockito.any(PersonProfileDTO.class))).thenReturn(personProfile);
        when(personProfileRepository.saveAndFlush(Mockito.any(PersonProfile.class))).thenReturn(personProfile);

        boolean isSaved = personProfileService.updateSummary(personProfileDTO, formPersonProfile);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void personProfileService_updateFacebook() {
        formPersonProfile.setFacebook("https://www.form-facebook.com");

        when(entityMapperService.mapDTOToPersonProfile(Mockito.any(PersonProfileDTO.class))).thenReturn(personProfile);
        when(personProfileRepository.saveAndFlush(Mockito.any(PersonProfile.class))).thenReturn(personProfile);

        boolean isSaved = personProfileService.updateFacebook(personProfileDTO, formPersonProfile);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void personProfileService_updateTwitter() {
        formPersonProfile.setTwitter("https://www.form-twitter.com");

        when(entityMapperService.mapDTOToPersonProfile(Mockito.any(PersonProfileDTO.class))).thenReturn(personProfile);
        when(personProfileRepository.saveAndFlush(Mockito.any(PersonProfile.class))).thenReturn(personProfile);

        boolean isSaved = personProfileService.updateTwitter(personProfileDTO, formPersonProfile);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void personProfileService_updateInstagram() {
        formPersonProfile.setInstagram("https://www.form-instagram.com");

        when(entityMapperService.mapDTOToPersonProfile(Mockito.any(PersonProfileDTO.class))).thenReturn(personProfile);
        when(personProfileRepository.saveAndFlush(Mockito.any(PersonProfile.class))).thenReturn(personProfile);

        boolean isSaved = personProfileService.updateInstagram(personProfileDTO, formPersonProfile);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void personProfileService_updateLinkedIn() {
        formPersonProfile.setLinkedIn("https://www.form-linkedin.com");

        when(entityMapperService.mapDTOToPersonProfile(Mockito.any(PersonProfileDTO.class))).thenReturn(personProfile);
        when(personProfileRepository.saveAndFlush(Mockito.any(PersonProfile.class))).thenReturn(personProfile);

        boolean isSaved = personProfileService.updateLinkedIn(personProfileDTO, formPersonProfile);

        Assertions.assertThat(isSaved).isTrue();
    }
}
