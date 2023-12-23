package com.iykescode.blog.mikeybloggingwebapp.service.impl;

import com.iykescode.blog.mikeybloggingwebapp.dto.TagDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Tag;
import com.iykescode.blog.mikeybloggingwebapp.repository.TagRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.EntityMapperService;
import com.iykescode.blog.mikeybloggingwebapp.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final EntityMapperService entityMapperService;

    @Override
    public List<TagDTO> findAllSortByAsc(String attribute) {
        Sort sort = Sort.by(Sort.Direction.ASC, attribute);
        List<Tag> tags = tagRepository.findAll(sort);
        return tags.stream()
                .map(entityMapperService::mapTagToDTO)
                .toList();
    }

    @Override
    public List<String> getTagNames() {
        StringBuilder resultStringBuilder = new StringBuilder();
        List<Tag> tags = tagRepository.findAll();

        for (Tag tag: tags) {
            resultStringBuilder.append(tag.getName()).append(",");
        }

        // Remove the trailing comma if the list is not empty
        if (!tags.isEmpty()) {
            resultStringBuilder.deleteCharAt(resultStringBuilder.length() - 1);
        }

        // Save the result to a String
        String resultStringTag = resultStringBuilder.toString();

        return List.of(resultStringTag.split(","));
    }

    @Override
    public void saveTag(Tag tag) {
        tagRepository.saveAndFlush(tag);
    }
}
