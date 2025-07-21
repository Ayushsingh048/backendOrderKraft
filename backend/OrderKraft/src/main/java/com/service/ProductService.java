package com.service;

import com.dto.ProductDTO;
import com.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(ProductDTO dto);               // Add new product
    List<Product> getAllProducts();                      // Get all products
    Optional<Product> getProductById(Long id);           // Get by ID
    Optional<Product> getProductByName(String name);     // Get by name
    List<Product> getProductsByCategory_id(Long category_id); // Get by category
}
