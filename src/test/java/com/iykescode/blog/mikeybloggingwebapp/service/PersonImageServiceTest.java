package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonImageDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.RoleDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Person;
import com.iykescode.blog.mikeybloggingwebapp.model.PersonImage;
import com.iykescode.blog.mikeybloggingwebapp.model.Role;
import com.iykescode.blog.mikeybloggingwebapp.repository.PersonImageRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.impl.PersonImageServiceImpl;
import com.iykescode.blog.mikeybloggingwebapp.util.ImageUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonImageServiceTest {

    @Mock
    private PersonImageRepository personImageRepository;
    @Mock
    private PersonService personService;
    @Mock
    private ImageUtil imageUtil;
    @Mock
    private EntityMapperService entityMapperService;

    @InjectMocks
    private PersonImageServiceImpl personImageService;

    private Person person;
    private PersonDTO personDTO;
    private final PersonImageDTO formPersonImage = new PersonImageDTO();
    private PersonImage personImage;
    private PersonImageDTO personImageDTO;

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
        personImage = PersonImage.builder()
                .id(1)
                .image("image.jpg")
                .build();
        personImageDTO = PersonImageDTO.builder()
                .id(1)
                .image("image.jpg")
                .build();
        person = Person.builder()
                .id(1)
                .firstName("Michael")
                .lastName("Jordan")
                .email("michaeljordan@yahoo.com")
                .username("michael_jordan")
                .password("admin")
                .role(role)
                .personImage(personImage)
                .build();
        personDTO = PersonDTO.builder()
                .id(1)
                .firstName("Michael")
                .lastName("Jordan")
                .email("michaeljordan@yahoo.com")
                .username("michael_jordan")
                .password("admin")
                .role(roleDTO)
                .personImage(personImageDTO)
                .build();
    }

    @Test
    public void personImageService_createNewImage() throws IOException {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "filename.jpg", "img/jpeg", file.getBytes());
        formPersonImage.setImageContent(mockMultipartFile);

        when(entityMapperService.mapDTOToPersonImage(Mockito.any(PersonImageDTO.class))).thenReturn(personImage);
        when(entityMapperService.mapDTOToPerson(Mockito.any(PersonDTO.class))).thenReturn(person);
        when(entityMapperService.mapPersonToDTO(Mockito.any(Person.class))).thenReturn(personDTO);
        when(personService.savePerson(Mockito.any(PersonDTO.class))).thenReturn(personDTO);
        when(personImageRepository.saveAndFlush(Mockito.any(PersonImage.class))).thenReturn(personImage);

        boolean isSaved = personImageService.createNewImage(personDTO, formPersonImage);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void personImageService_updateImage() throws IOException {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "filename.jpg", "image/png", file.getBytes());
        formPersonImage.setImageContent(mockMultipartFile);

        when(entityMapperService.mapDTOToPersonImage(Mockito.any(PersonImageDTO.class))).thenReturn(personImage);
        when(personImageRepository.saveAndFlush(Mockito.any(PersonImage.class))).thenReturn(personImage);

        boolean isSaved = personImageService.updateImage(formPersonImage, personImageDTO);

        Assertions.assertThat(isSaved).isTrue();
    }
}
