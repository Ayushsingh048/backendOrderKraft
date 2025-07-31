package com.service;

import com.dto.ProductDTO;
import com.entity.Category;
import com.entity.Product;
import com.entity.User;
import com.repository.CategoryRepository;
import com.repository.ProductRepository;
import com.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public Product createProduct(ProductDTO dto) {
        // Fetch associated Category
        Category category = categoryRepo.findById(dto.getCategory_id())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + dto.getCategory_id()));

        // Fetch associated Production Manager (User)
        User manager = userRepo.findById(dto.getProduction_manager_id())
                .orElseThrow(() -> new RuntimeException("Production manager not found with ID: " + dto.getProduction_manager_id()));

        // Map DTO to Entity
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setUnit_price(dto.getUnit_price());
        product.setCategory(category);
        product.setProductionManager(manager);

        return productRepo.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepo.findById(id);
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<Product> getProductsByCategory_id(Long category_id) {
        return productRepo.findByCategory_CategoryId(category_id);
    }
    
    @Override
    public List<Product> getProductsByProductionManagerId(Long productionManagerId) {
        return productRepo.findByProductionManager_Id(productionManagerId);
    }

}
