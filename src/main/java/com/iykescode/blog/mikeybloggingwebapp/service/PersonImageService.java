package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonImageDTO;

import java.io.IOException;

public interface PersonImageService {

    boolean createNewImage(PersonDTO personDTO, PersonImageDTO personImageDTO) throws IOException;
    boolean updateImage(PersonImageDTO personImageDTO, PersonImageDTO dbPersonImage) throws IOException;
}
