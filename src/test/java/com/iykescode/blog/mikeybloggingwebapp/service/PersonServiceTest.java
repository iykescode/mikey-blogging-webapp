package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.RoleDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Person;
import com.iykescode.blog.mikeybloggingwebapp.model.Role;
import com.iykescode.blog.mikeybloggingwebapp.repository.PersonRepository;
import com.iykescode.blog.mikeybloggingwebapp.repository.RoleRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.impl.PersonServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private EntityMapperService entityMapperService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PersonServiceImpl personService;

    private Role role;
    private Person person;
    private Person person2;
    private PersonDTO personDTO;
    private PersonDTO personDTO2;

    @BeforeEach
    public void init() {
        role = Role.builder()
                .id(1)
                .roleName("ROLE_ADMIN")
                .build();
        RoleDTO roleDTO = RoleDTO.builder()
                .id(1)
                .roleName("ROLE_ADMIN")
                .build();
        Role role2 = Role.builder()
                .id(1)
                .roleName("ROLE_USER")
                .build();
        RoleDTO roleDTO2 = RoleDTO.builder()
                .id(1)
                .roleName("ROLE_USER")
                .build();
        person = Person.builder()
                .id(1)
                .firstName("Michael")
                .lastName("Jordan")
                .email("michaeljordan@yahoo.com")
                .username("michael_jordan")
                .password("admin")
                .role(role)
                .build();
        person2 = Person.builder()
                .id(2)
                .firstName("Michelle")
                .lastName("Obama")
                .email("michelleobama@usa.gov")
                .username("michelle_obama")
                .password("admin")
                .role(role2)
                .build();
        personDTO = PersonDTO.builder()
                .id(1)
                .firstName("Michael")
                .lastName("Jordan")
                .email("michaeljordan@yahoo.com")
                .username("michael_jordan")
                .password("admin")
                .role(roleDTO)
                .build();
        personDTO2 = PersonDTO.builder()
                .id(2)
                .firstName("Michelle")
                .lastName("Obama")
                .email("michelleobama@usa.gov")
                .username("michelle_obama")
                .password("admin")
                .role(roleDTO2)
                .build();
    }

    @Test
    public void personService_createNewPerson() {
        when(roleRepository.getByRoleName(Mockito.any(String.class))).thenReturn(role);
        when(entityMapperService.mapDTOToPerson(Mockito.any(PersonDTO.class))).thenReturn(person);
        when(personRepository.saveAndFlush(Mockito.any(Person.class))).thenReturn(person);

        boolean isSaved = personService.createNewPerson(personDTO);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void personService_updatePersonName() {
        when(personRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(person));
        when(personRepository.saveAndFlush(Mockito.any(Person.class))).thenReturn(person);

        boolean isSaved = personService.updatePersonName(personDTO.getId(), personDTO);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void personService_updatePersonEmail() {
        when(personRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(person));
        when(personRepository.saveAndFlush(Mockito.any(Person.class))).thenReturn(person);

        boolean isSaved = personService.updatePersonEmail(personDTO.getId(), personDTO);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void personService_updatePersonUsername() {
        when(personRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(person));
        when(personRepository.saveAndFlush(Mockito.any(Person.class))).thenReturn(person);

        boolean isSaved = personService.updatePersonUsername(personDTO.getId(), personDTO);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void personService_updatePersonPassword() {
        when(personRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(person));
        when(personRepository.saveAndFlush(Mockito.any(Person.class))).thenReturn(person);

        boolean isSaved = personService.updatePersonPassword(personDTO.getId(), personDTO);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void personService_savePerson() {
        when(entityMapperService.mapDTOToPerson(Mockito.any(PersonDTO.class))).thenReturn(person);
        when(entityMapperService.mapPersonToDTO(Mockito.any(Person.class))).thenReturn(personDTO);
        when(personRepository.saveAndFlush(Mockito.any(Person.class))).thenReturn(person);

        PersonDTO dto = personService.savePerson(personDTO);

        Assertions.assertThat(dto).isNotNull();
    }

    @Test
    public void personService_readByEmail() {
        when(personRepository.readByEmail(Mockito.any(String.class))).thenReturn(Optional.ofNullable(person));
        when(entityMapperService.mapPersonToDTO(Mockito.any(Person.class))).thenReturn(personDTO);

        Optional<PersonDTO> dto = personService.readByEmail(person.getEmail());

        Assertions.assertThat(dto).isNotNull();
    }

    @Test
    public void personService_readByUsername() {
        when(personRepository.readByUsername(Mockito.any(String.class))).thenReturn(Optional.ofNullable(person));
        when(entityMapperService.mapPersonToDTO(Mockito.any(Person.class))).thenReturn(personDTO);

        Optional<PersonDTO> dto = personService.readByUsername(person.getUsername());

        Assertions.assertThat(dto).isNotNull();
    }

    @Test
    public void personService_deleteByPersonId() {
        when(personRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(person));
        doNothing().when(personRepository).delete(Mockito.any(Person.class));

        assertAll(() -> personService.deleteByPersonId(personDTO.getId()));
    }

    @Test
    public void personService_findAll() {
        List<Person> people = Arrays.asList(
                person,
                person2
        );
        when(personRepository.findAll()).thenReturn(people);
        when(entityMapperService.mapPersonToDTO(Mockito.any(Person.class))).thenReturn(personDTO);

        List<PersonDTO> dto = personService.findAll();

        Assertions.assertThat(dto).size().isGreaterThanOrEqualTo(1);
    }

    @Test
    public void personService_findByPersonId() {
        when(personRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(person2));
        when(entityMapperService.mapPersonToDTO(Mockito.any(Person.class))).thenReturn(personDTO2);

        PersonDTO dto = personService.findByPersonId(person2.getId());

        Assertions.assertThat(dto).isNotNull();
    }
}
