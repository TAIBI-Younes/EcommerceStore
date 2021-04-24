package com.ecommerce.store.service;

import com.ecommerce.store.model.Category;
import com.ecommerce.store.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

class CategoryServiceTest {


    @InjectMocks
    CategoryService categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
        System.out.println("the test is End");
    }

    @Test
    void getCategory() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("category_1");

        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(category1));

        Assert.assertEquals(categoryService.getCategory(1l), category1);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void getcategories() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("category_1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("category_2");

        Map<String, String> filters = new HashMap<>();
        filters.put("size", "1");
        filters.put("page", "0");

        List<Category> listCategory = new LinkedList<>();
        listCategory.add(category1);
        Page<Category> categoryPage = new PageImpl<>(listCategory);
        when(categoryRepository.findAll(PageRequest.of(Integer.parseInt(filters.get("page")), Integer.parseInt(filters.get("size"))))).thenReturn(categoryPage);

        Assert.assertEquals(categoryService.getCategories(filters).get().findFirst().get(), category1);
        assertThat(categoryService.getCategories(filters), is(categoryPage));
        assertThat(categoryService.getCategories(filters).getTotalElements(), is(1L));
        verify(categoryRepository, atLeast(1)).findAll(PageRequest.of(Integer.parseInt(filters.get("page")), Integer.parseInt(filters.get("size"))));
    }

    @Test
    void createCategory() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("category_1");

        when(categoryRepository.save(category1)).thenReturn(category1);

        Assert.assertEquals(categoryService.createCategory(category1), category1);
        verify(categoryRepository, times(1)).save(category1);

    }

    @Test
    void updateCategory() {
        Category oldCategory = new Category();
        oldCategory.setId(1L);
        oldCategory.setName("category_1");

        Category newCategory = new Category();
        newCategory.setId(1L);
        newCategory.setName("category_2");

        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(oldCategory));
        when(categoryRepository.save(oldCategory)).thenReturn(oldCategory);

        Assert.assertEquals(categoryService.updateCategory(1L, newCategory), newCategory);
        verify(categoryRepository, times(1)).save(oldCategory);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void deleteCategoryById() {
        Category category = new Category();
        category.setId(1L);
        category.setName("category_1");

        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(category));
        Assert.assertEquals(categoryService.deleteCategory(1l), category);
        verify(categoryRepository, times(1)).deleteById(1L);

    }

    @Test
    void getPhoto() {
        Category category = new Category();
        category.setId(1L);
        category.setName("category_1");
        byte[] photo = new byte[12];
        category.setPhoto(photo);

        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(category));
        Assert.assertEquals(categoryService.getPhoto(1l), photo);

        verify(categoryRepository, times(1)).findById(1L);

    }

    @Test
    void uploadPhoto() {
        Category category = new Category();
        category.setId(1L);
        category.setName("category_1");
        byte[] oldPhoto = new byte[12];
        category.setPhoto(oldPhoto);
        byte[] newPhoto = new byte[12];
        Category upDateCategory = new Category();
        upDateCategory.setId(1L);
        upDateCategory.setName("category_1");
        upDateCategory.setPhoto(newPhoto);

        when(categoryRepository.findById(1L)).thenReturn(java.util.Optional.of(upDateCategory));
        when(categoryRepository.save(upDateCategory)).thenReturn(upDateCategory);
        Assert.assertEquals(categoryService.uploadPhoto(newPhoto, 1L), upDateCategory);

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(category);

    }

}