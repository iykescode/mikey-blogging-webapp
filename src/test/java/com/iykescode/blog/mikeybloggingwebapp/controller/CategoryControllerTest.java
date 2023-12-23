package com.iykescode.blog.mikeybloggingwebapp.controller;

import com.iykescode.blog.mikeybloggingwebapp.dto.CategoryDTO;
import com.iykescode.blog.mikeybloggingwebapp.repository.CategoryRepository;
import com.iykescode.blog.mikeybloggingwebapp.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private CategoryService categoryService;

    private CategoryDTO formCategory;

    @BeforeEach
    public void init() {
        formCategory = CategoryDTO.builder()
                .id(1)
                .name("Science")
                .build();
    }

    @Test
    public void categoryController_viewAllCategories() throws Exception {
        mockMvc.perform(get("/user/categories"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/categories"));
    }

    @Test
    public void categoryController_createCategoryPage() throws Exception {
        mockMvc.perform(get("/user/categories/create"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/category-form"));
    }

    @Test
    public void categoryController_createCategory() throws Exception {
        given(categoryService.createCategory(ArgumentMatchers.any(CategoryDTO.class))).willAnswer(invocationOnMock -> true);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", formCategory.getName());

        mockMvc.perform(post("/user/categories/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/categories?message=created"));
    }

    @Test
    public void categoryController_deleteCategory() throws Exception {
        doNothing().when(categoryService).deleteByCategoryId(ArgumentMatchers.any(Integer.class));

        mockMvc.perform(delete(String.format("/user/categories/delete/%s", formCategory.getId()))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/categories?message=deleted"));
    }
}
