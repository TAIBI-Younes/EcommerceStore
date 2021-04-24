package com.ecommerce.store.controller;


import com.ecommerce.store.model.Category;
import com.ecommerce.store.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Autowired
    @InjectMocks
    CategoryController categoryController;

    @Mock
    CategoryService categoryService;

    private MockMvc mockMvc;

    private JacksonTester<Category> jsonCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders
                .standaloneSetup(categoryController)
                .build();
    }


    @Test
    void getPhoto() throws Exception {
        Category category = new Category();
        byte[] photo = new byte[0];
        category.setId(1L);
        category.setPhoto(photo);

        when(categoryService.getPhoto(1L)).thenReturn(photo);
        MockHttpServletResponse response = mockMvc.perform(get("/photoCategory/1")
                .accept(MediaType.ALL))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsByteArray()).isEqualTo(photo);
        verify(categoryService, times(1)).getPhoto(1L);
    }

    @Test
    void uploadPhoto() throws Exception {
        /*
        Category category_1 = new Category();
        byte[] photo = new byte[0];
        category_1.setId(1L);
        Category category_2 = new Category();

        category_2.setId(1L);
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.png");
        final MockMultipartFile avatar = new MockMultipartFile("test.png", "test.png", "image/png", inputStream);
        category_2.setPhoto(avatar.getBytes());
        when(categoryService.uploadPhoto(photo, 1L)).thenReturn(category_2);

        MockHttpServletResponse response = mockMvc.perform(put("/categories/uploadPhoto/1")
                .content(avatar.getBytes()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsByteArray()).isEqualTo(category_2);
        verify(categoryService, times(1)).uploadPhoto(photo, 1L);
        */
    }


    @Test
    void getCategory() throws Exception{
        Category category = new Category();
        category.setId(1L);

        when(categoryService.getCategory(1L)).thenReturn(category);
        MockHttpServletResponse response = mockMvc.perform(get("/categories/1")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonCategory.write(category).getJson());
    }

    @Test
    void getCategories() throws Exception{
        Category category_1 = new Category();
        category_1.setId(1L);

        Category category_2 = new Category();
        category_2.setId(2L);

        Map<String, String> filters = new HashMap<>();

        List<Category> categories = new ArrayList<>();
        categories.add(category_1);
        categories.add(category_2);

        Page<Category> categoryPage = new PageImpl<>(categories);

        when(categoryService.getCategories(filters)).thenReturn(categoryPage);
        MockHttpServletResponse response = mockMvc.perform(get("/categories")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.content.[0].id", CoreMatchers.is(1)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(categoryService, times(1)).getCategories(filters);

    }

    @Test
    void createCategory() throws Exception {
        Category category = new Category();
        category.setId(1L);

        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(category);

        mockMvc.perform(post("/categories")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonContent))
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        verify(categoryService, times(1)).createCategory(category);

    }

    @Test
    void updateCategory() throws Exception{

        Category category = new Category();
        category.setId(1L);
        category.setName("name_1");
        Category upDateCategory = new Category();
        upDateCategory.setId(1L);
        upDateCategory.setName("name_2");

        when(categoryService.updateCategory(1L, upDateCategory)).thenReturn(upDateCategory);

        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(upDateCategory);

        mockMvc.perform(put("/categories/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.is(upDateCategory.getName())))
                .andReturn().getResponse().getContentAsString();

        verify(categoryService, times(1)).updateCategory(1L, upDateCategory);

    }

    @Test
    void deleteCategory() throws Exception{
        Category category = new Category();
        category.setId(1L);

        when(categoryService.deleteCategory(1L)).thenReturn(category);
        MockHttpServletResponse response = mockMvc.perform(delete("/categories/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(categoryService, times(1)).deleteCategory(1L);
    }
}