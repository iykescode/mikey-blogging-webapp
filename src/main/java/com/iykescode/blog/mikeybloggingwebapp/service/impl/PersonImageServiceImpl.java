package com.iykescode.blog.mikeybloggingwebapp.service.impl;

import com.iykescode.blog.mikeybloggingwebapp.constants.ImageDIRConstant;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonImageDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Person;
import com.iykescode.blog.mikeybloggingwebapp.model.PersonImage;
import com.iykescode.blog.mikeybloggingwebapp.repository.PersonImageRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.EntityMapperService;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonImageService;
import com.iykescode.blog.mikeybloggingwebapp.service.PersonService;
import com.iykescode.blog.mikeybloggingwebapp.util.ImageUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@AllArgsConstructor
public class PersonImageServiceImpl implements PersonImageService {

    private final PersonImageRepository personImageRepository;
    private final PersonService personService;
    private final ImageUtil imageUtil;
    private final EntityMapperService entityMapperService;

    @Override
    public boolean createNewImage(PersonDTO personDTO, PersonImageDTO personImageDTO) throws IOException {
        boolean isSaved = false;
        String uploadedImage = imageUtil.uploadImage(personImageDTO.getImageContent(), ImageDIRConstant.userImages);

        PersonImage personImage = entityMapperService.mapDTOToPersonImage(personImageDTO);
        personImage.setImage(uploadedImage);
        PersonImage savedImage = personImageRepository.saveAndFlush(personImage);

        Person person = entityMapperService.mapDTOToPerson(personDTO);

        if(savedImage.getId() > 0) {
            person.setPersonImage(savedImage);
        }

        PersonDTO savedPerson = personService.savePerson(entityMapperService.mapPersonToDTO(person));

        if (savedPerson.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public boolean updateImage(PersonImageDTO personImageDTO, PersonImageDTO dbPersonImage) throws IOException {
        boolean isSaved = false;
        String uploadedImage = imageUtil.uploadImage(personImageDTO.getImageContent(), ImageDIRConstant.userImages);

        File file = new File(ImageDIRConstant.userImages + dbPersonImage.getImage());
        if (file.exists()) {
            file.delete();
        }

        PersonImage mappedPersonImage = entityMapperService.mapDTOToPersonImage(dbPersonImage);
        mappedPersonImage.setImage(uploadedImage);
        PersonImage savedImage = personImageRepository.saveAndFlush(mappedPersonImage);

        if (savedImage.getId() > 0)
            isSaved = true;

        return isSaved;
    }
}
