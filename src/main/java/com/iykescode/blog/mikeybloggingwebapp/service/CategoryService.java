package com.iykescode.blog.mikeybloggingwebapp.service;

import com.iykescode.blog.mikeybloggingwebapp.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> findAllSortByAsc(String attribute);
    CategoryDTO findByCategoryId(Integer id);
    boolean createCategory(CategoryDTO categoryDTO);
    void deleteByCategoryId(Integer id);
}
