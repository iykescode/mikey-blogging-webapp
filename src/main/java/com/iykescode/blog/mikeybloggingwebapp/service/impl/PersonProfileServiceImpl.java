package com.iykescode.blog.mikeybloggingwebapp.service.impl;

import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonProfileDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Person;
import com.iykescode.blog.mikeybloggingwebapp.model.PersonProfile;
import com.iykescode.blog.mikeybloggingwebapp.repository.PersonProfileRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.EntityMapperService;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonProfileService;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PersonProfileServiceImpl implements PersonProfileService {

    private final PersonProfileRepository personProfileRepository;
    private final PersonService personService;
    private final EntityMapperService entityMapperService;

    @Override
    public boolean createProfile(PersonDTO personDTO, PersonProfileDTO personProfileDTO) {
        boolean isSaved = false;
        PersonProfile personProfile = entityMapperService.mapDTOToPersonProfile(personProfileDTO);
        PersonProfile savedProfile = personProfileRepository.saveAndFlush(personProfile);
        Person person = entityMapperService.mapDTOToPerson(personDTO);
        person.setPersonProfile(savedProfile);
        PersonDTO savedPerson = personService.savePerson(entityMapperService.mapPersonToDTO(person));

        if(savedPerson.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean updateHeadline(PersonProfileDTO personProfileDTO, PersonProfileDTO formPersonProfile) {
        boolean isSaved = false;
        PersonProfile personProfile = entityMapperService.mapDTOToPersonProfile(personProfileDTO);
        personProfile.setHeadline(formPersonProfile.getHeadline());
        PersonProfile savedProfile = personProfileRepository.saveAndFlush(personProfile);

        if(savedProfile.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean updateSummary(PersonProfileDTO personProfileDTO, PersonProfileDTO formPersonProfile) {
        boolean isSaved = false;
        PersonProfile personProfile = entityMapperService.mapDTOToPersonProfile(personProfileDTO);
        personProfile.setSummary(formPersonProfile.getSummary());
        PersonProfile savedProfile = personProfileRepository.saveAndFlush(personProfile);

        if(savedProfile.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean updateFacebook(PersonProfileDTO personProfileDTO, PersonProfileDTO formPersonProfile) {
        boolean isSaved = false;
        PersonProfile personProfile = entityMapperService.mapDTOToPersonProfile(personProfileDTO);
        personProfile.setFacebook(formPersonProfile.getFacebook());
        PersonProfile savedProfile = personProfileRepository.saveAndFlush(personProfile);

        if(savedProfile.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean updateTwitter(PersonProfileDTO personProfileDTO, PersonProfileDTO formPersonProfile) {
        boolean isSaved = false;
        PersonProfile personProfile = entityMapperService.mapDTOToPersonProfile(personProfileDTO);
        personProfile.setTwitter(formPersonProfile.getTwitter());
        PersonProfile savedProfile = personProfileRepository.saveAndFlush(personProfile);

        if(savedProfile.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean updateInstagram(PersonProfileDTO personProfileDTO, PersonProfileDTO formPersonProfile) {
        boolean isSaved = false;
        PersonProfile personProfile = entityMapperService.mapDTOToPersonProfile(personProfileDTO);
        personProfile.setInstagram(formPersonProfile.getInstagram());
        PersonProfile savedProfile = personProfileRepository.saveAndFlush(personProfile);

        if(savedProfile.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean updateLinkedIn(PersonProfileDTO personProfileDTO, PersonProfileDTO formPersonProfile) {
        boolean isSaved = false;
        PersonProfile personProfile = entityMapperService.mapDTOToPersonProfile(personProfileDTO);
        personProfile.setLinkedIn(formPersonProfile.getLinkedIn());
        PersonProfile savedProfile = personProfileRepository.saveAndFlush(personProfile);

        if(savedProfile.getId() > 0)
            isSaved = true;

        return isSaved;
    }
}
