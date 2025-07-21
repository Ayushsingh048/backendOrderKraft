package com.service;

import com.dto.CategoryDTO;
import com.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(CategoryDTO dto); // Create new category
    List<Category> getAllCategories();        // Fetch all categories
    Optional<Category> getCategoryById(Long id); // Fetch category by ID
}
