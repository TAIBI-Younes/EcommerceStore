package com.ecommerce.store.controller;

import com.ecommerce.store.model.Category;
import com.ecommerce.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(path = "/photoCategory/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("id") Long id) {
        return categoryService.getPhoto(id);
    }

    @PutMapping(path = "/categories/uploadPhoto/{id}")
    public void uploadPhoto(@RequestBody byte[] image, @PathVariable Long id) {
        categoryService.uploadPhoto(image, id);
    }

    @GetMapping("/categories/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @GetMapping("/categories")
    public Page<Category> getCategorys(@RequestParam Map<String, String> filters) {
        return categoryService.getCategories(filters);
    }

    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("/categories/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/categories/{id}")
    public Category deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }

    @DeleteMapping("/categories")
    public void deleteCategory(@RequestBody Category category) {
        categoryService.deleteCategory(category);
    }

}
