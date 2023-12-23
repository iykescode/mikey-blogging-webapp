package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.TagDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Tag;
import com.iykescode.blog.mikeybloggingwebapp.repository.TagRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.impl.TagServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;
    @Mock
    private EntityMapperService entityMapperService;

    @InjectMocks
    private TagServiceImpl tagService;

    private Tag tag1;
    private Tag tag2;
    private Tag tag3;
    private Tag tag4;
    private Tag tag5;

    @BeforeEach
    public void init() {
        tag1 = Tag.builder()
                .id(1)
                .name("Humans")
                .build();
        tag2 = Tag.builder()
                .id(2)
                .name("Creativity")
                .build();
        tag3 = Tag.builder()
                .id(3)
                .name("Science")
                .build();
        tag4 = Tag.builder()
                .id(4)
                .name("Company")
                .build();
        tag5 = Tag.builder()
                .id(5)
                .name("Branding")
                .build();
    }

    @Test
    public void tagService_findAllSortByAsc() {
        List<Tag> tags = Arrays.asList(
                tag1,
                tag2,
                tag3,
                tag4,
                tag5
        );

        when(tagRepository.findAll(Mockito.any(Sort.class))).thenReturn(tags);

        List<TagDTO> allSortByAsc = tagService.findAllSortByAsc("name");

        Assertions.assertThat(allSortByAsc).size().isGreaterThanOrEqualTo(1);
    }

    @Test
    public void tagService_getTagNames() {
        List<Tag> tags = Arrays.asList(
                tag1,
                tag2,
                tag3,
                tag4,
                tag5
        );

        when(tagRepository.findAll()).thenReturn(tags);

        List<String> tagNames = tagService.getTagNames();

        Assertions.assertThat(tagNames).size().isGreaterThanOrEqualTo(1);
    }

    @Test
    public void tagService_saveTag() {
        when(tagRepository.saveAndFlush(Mockito.any(Tag.class))).thenReturn(tag1);

        assertAll(() ->tagService.saveTag(tag1));
    }
}
