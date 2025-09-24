package com.service;

import com.dto.CategoryDTO;
import com.entity.Category;
import com.entity.User;
import com.repository.CategoryRepository;
import com.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    @Override
    public Category createCategory(CategoryDTO dto) {
        // Map DTO to Entity
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        return categoryRepo.save(category); // Save to DB
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepo.findById(id);
    }
}
