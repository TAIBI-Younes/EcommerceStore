package com.ecommerce.store.service;

import com.ecommerce.store.exception.ResourceNotFoundException;
import com.ecommerce.store.model.Category;
import com.ecommerce.store.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id " + id.toString() + " doesn't exist."));
    }

    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public Page<Category> getCategories(Map<String, String> filters) {
        return categoryRepository.findAll(PageRequest.of(filters.get("page") != null ? Integer.parseInt(filters.get("page")) : 0, filters.get("size") != null ? Integer.parseInt(filters.get("size")) : 10));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Category updateCategory(Long id, Category category) {
        Category oldCategory = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id " + id.toString() + " doesn't exist."));
        oldCategory.setDescription(category.getDescription());
        oldCategory.setName(category.getName());
        return categoryRepository.save(oldCategory);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Category deleteCategory(Long id) {
        Category Category = getCategory(id);
        categoryRepository.deleteById(id);
        return Category;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    public void deleteAllCategory() {
        categoryRepository.deleteAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public byte[] getPhoto(Long id) {
        Category Category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id " + id.toString() + " doesn't exist."));
        return Category.getPhoto();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Category uploadPhoto(byte[] image, Long id) {
        Category Category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with id " + id.toString() + " doesn't exist."));
        Category.setPhoto(image);
        return categoryRepository.save(Category);
    }

}
