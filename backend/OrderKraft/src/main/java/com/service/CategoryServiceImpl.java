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

    @Autowired
    private UserRepository userRepo;

    @Override
    public Category createCategory(CategoryDTO dto) {
        // Fetch the inventory manager using ID
        User manager = userRepo.findById(dto.getInventory_manager_id())
                .orElseThrow(() -> new RuntimeException("Inventory Manager not found"));

        // Map DTO to Entity
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setInventoryManager(manager);

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
