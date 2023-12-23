package com.iykescode.blog.mikeybloggingwebapp.service;


import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    PersonDTO getLoggedInUser();
    boolean createNewPerson(PersonDTO personDTO);
    boolean updatePersonName(Integer id, PersonDTO personDTO);
    boolean updatePersonEmail(Integer id, PersonDTO personDTO);
    boolean updatePersonUsername(Integer id, PersonDTO personDTO);
    boolean updatePersonPassword(Integer id, PersonDTO personDTO);
    PersonDTO savePerson(PersonDTO personDTO);
    Optional<PersonDTO> readByEmail(String email);
    Optional<PersonDTO> readByUsername(String username);
    void deleteByPersonId(Integer id);
    List<PersonDTO> findAll();
    PersonDTO findByPersonId(Integer id);
}
