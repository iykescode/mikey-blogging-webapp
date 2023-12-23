package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.*;
import com.iykescode.blog.mikeybloggingwebapp.model.*;
import com.iykescode.blog.mikeybloggingwebapp.repository.CategoryRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.impl.CategoryServiceImpl;
import com.iykescode.blog.mikeybloggingwebapp.util.StringUtil;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private EntityMapperService entityMapperService;
    @Mock
    private StringUtil stringUtil;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryDTO categoryDTO;
    private Category category;
    private Category category2;
    private Category category3;

    @BeforeEach
    public void init() {
        categoryDTO = CategoryDTO.builder()
                .id(1)
                .name("Category 1")
                .build();
        category = Category.builder()
                .id(1)
                .name("Category 1")
                .build();
        category2 = Category.builder()
                .id(2)
                .name("Category 2")
                .build();
        category3 = Category.builder()
                .id(3)
                .name("Category 3")
                .build();
    }

    @Test
    public void categoryService_findAllSortByAsc() {
        List<Category> categories = Arrays.asList(
                category,
                category2,
                category3
        );

        when(categoryRepository.findAll(Mockito.any(Sort.class))).thenReturn(categories);

        List<CategoryDTO> allSortByAsc = categoryService.findAllSortByAsc("createdAt");

        Assertions.assertThat(allSortByAsc).size().isGreaterThanOrEqualTo(1);
    }

    @Test
    public void categoryService_findByCategoryId() {
        when(entityMapperService.mapCategoryToDTO(Mockito.any(Category.class))).thenReturn(categoryDTO);
        when(categoryRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(category));

        CategoryDTO byCategoryId = categoryService.findByCategoryId(categoryDTO.getId());

        Assertions.assertThat(byCategoryId).isNotNull();
    }

    @Test
    public void categoryService_createCategory() {
        when(entityMapperService.mapDTOToCategory(Mockito.any(CategoryDTO.class))).thenReturn(category);
        when(entityMapperService.mapCategoryToDTO(Mockito.any(Category.class))).thenReturn(categoryDTO);
        when(categoryRepository.saveAndFlush(Mockito.any(Category.class))).thenReturn(category);

        boolean isSaved = categoryService.createCategory(categoryDTO);

        Assertions.assertThat(isSaved).isTrue();
    }

    @Test
    public void categoryService_deleteByCategoryId() {
        when(categoryRepository.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(category));
        doNothing().when(categoryRepository).delete(Mockito.any(Category.class));

        assertAll(() -> categoryService.deleteByCategoryId(categoryDTO.getId()));
    }
}
