package com.iykescode.blog.mikeybloggingwebapp.service.impl;

import com.iykescode.blog.mikeybloggingwebapp.constants.UserRoles;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Person;
import com.iykescode.blog.mikeybloggingwebapp.model.Role;
import com.iykescode.blog.mikeybloggingwebapp.repository.PersonRepository;
import com.iykescode.blog.mikeybloggingwebapp.repository.RoleRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.EntityMapperService;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapperService entityMapperService;

    @Override
    public PersonDTO getLoggedInUser() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Person> byEmail = personRepository.readByEmail(user);
        Optional<Person> byUsername = personRepository.readByUsername(user);
        Person person = byEmail.orElseGet(() -> byUsername.orElse((null)));
        return entityMapperService.mapPersonToDTO(person);
    }

    @Override
    public boolean createNewPerson(PersonDTO personDTO) {
        boolean isSaved = false;
        Role role = roleRepository.getByRoleName(String.valueOf(UserRoles.ROLE_USER));
        Person person = entityMapperService.mapDTOToPerson(personDTO);
        person.setRole(role);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person = personRepository.saveAndFlush(person);

        if(person.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean updatePersonName(Integer id, PersonDTO personDTO) {
        boolean isSaved = false;
        Person dbPerson = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Person not found"));
        dbPerson.setFirstName(personDTO.getFirstName());
        dbPerson.setLastName(personDTO.getLastName());
        Person savedPerson = personRepository.saveAndFlush(dbPerson);

        if(savedPerson.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean updatePersonEmail(Integer id, PersonDTO personDTO) {
        boolean isSaved = false;
        Person dbPerson = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Person not found"));
        dbPerson.setEmail(personDTO.getEmail());
        Person savedPerson = personRepository.saveAndFlush(dbPerson);

        if(savedPerson.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean updatePersonUsername(Integer id, PersonDTO personDTO) {
        boolean isSaved = false;
        Person dbPerson = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Person not found"));
        dbPerson.setUsername(personDTO.getUsername());
        Person savedPerson = personRepository.saveAndFlush(dbPerson);

        if(savedPerson.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean updatePersonPassword(Integer id, PersonDTO personDTO) {
        boolean isSaved = false;
        Person dbPerson = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Person not found"));
        dbPerson.setPassword(passwordEncoder.encode(personDTO.getPassword()));
        Person savedPerson = personRepository.saveAndFlush(dbPerson);

        if(savedPerson.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public PersonDTO savePerson(PersonDTO personDTO) {
        Person person = entityMapperService.mapDTOToPerson(personDTO);
        Person savedPerson = personRepository.saveAndFlush(person);
        return entityMapperService.mapPersonToDTO(savedPerson);
    }

    @Override
    public Optional<PersonDTO> readByEmail(String email) {
        Optional<Person> person = personRepository.readByEmail(email);
        return person.map(entityMapperService::mapPersonToDTO);
    }

    @Override
    public Optional<PersonDTO> readByUsername(String username) {
        Optional<Person> person = personRepository.readByUsername(username);
        return person.map(entityMapperService::mapPersonToDTO);
    }

    @Override
    public void deleteByPersonId(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Person not found"));
        personRepository.delete(person);
    }

    @Override
    public List<PersonDTO> findAll() {
        List<Person> people = personRepository.findAll();
        return people.stream()
                .map(entityMapperService::mapPersonToDTO)
                .toList();
    }

    @Override
    public PersonDTO findByPersonId(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Person not found"));
        return entityMapperService.mapPersonToDTO(person);
    }
}
