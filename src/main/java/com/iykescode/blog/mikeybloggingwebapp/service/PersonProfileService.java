package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.PersonDTO;
import com.iykescode.blog.mikeybloggingwebapp.dto.PersonProfileDTO;

public interface PersonProfileService {

    boolean createProfile(PersonDTO personDTO, PersonProfileDTO personProfileDTO);
    boolean updateHeadline(PersonProfileDTO personProfileDTO, PersonProfileDTO formPersonProfile);
    boolean updateSummary(PersonProfileDTO personProfileDTO, PersonProfileDTO formPersonProfile);
    boolean updateFacebook(PersonProfileDTO personProfileDTO, PersonProfileDTO formPersonProfile);
    boolean updateTwitter(PersonProfileDTO personProfileDTO, PersonProfileDTO formPersonProfile);
    boolean updateInstagram(PersonProfileDTO personProfileDTO, PersonProfileDTO formPersonProfile);
    boolean updateLinkedIn(PersonProfileDTO personProfileDTO, PersonProfileDTO formPersonProfile);
}
