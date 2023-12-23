package com.iykescode.blog.mikeybloggingwebapp.service.impl;

import com.iykescode.blog.mikeybloggingwebapp.dto.CategoryDTO;
import com.iykescode.blog.mikeybloggingwebapp.model.Category;
import com.iykescode.blog.mikeybloggingwebapp.repository.CategoryRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.CategoryService;
import com.iykescode.blog.mikeybloggingwebapp.service.EntityMapperService;
import com.iykescode.blog.mikeybloggingwebapp.util.StringUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final StringUtil stringUtil;
    private final EntityMapperService entityMapperService;

    @Override
    public List<CategoryDTO> findAllSortByAsc(String attribute) {
        Sort sort = Sort.by(Sort.Direction.ASC, attribute);
        List<Category> categoryList = categoryRepository.findAll(sort);
        return categoryList.stream()
                .map(entityMapperService::mapCategoryToDTO)
                .toList();
    }

    @Override
    public CategoryDTO findByCategoryId(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return entityMapperService.mapCategoryToDTO(category);
    }

    @Override
    public boolean createCategory(CategoryDTO categoryDTO) {
        boolean isSaved = false;
        String name = stringUtil.capitalizeFirstLetter(categoryDTO.getName());
        categoryDTO.setName(name);

        Category category = entityMapperService.mapDTOToCategory(categoryDTO);
        Category savedCategory = categoryRepository.saveAndFlush(category);

        CategoryDTO mappedCategoryDTO = entityMapperService.mapCategoryToDTO(savedCategory);

        if(mappedCategoryDTO.getId() > 0)
            isSaved = true;

        return isSaved;
    }

    @Override
    public void deleteByCategoryId(Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }
}
