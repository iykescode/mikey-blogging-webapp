package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.TagDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Tag;

import java.util.List;

public interface TagService {

    List<TagDTO> findAllSortByAsc(String attribute);
    List<String> getTagNames();
    void saveTag(Tag tag);
}
