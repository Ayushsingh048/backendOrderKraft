package com.controller;

import com.dto.ProductDTO;
import com.dto.ProductDTOCustom;
import com.entity.Product;
import com.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Add new product
    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    // Get all products
    @GetMapping("/all")
    public ResponseEntity<List<ProductDTOCustom>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get product by name
    @GetMapping("/search/name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        return productService.getProductByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get products by category ID
    @GetMapping("/search/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory_id(@PathVariable Long category_id) {
        return ResponseEntity.ok(productService.getProductsByCategory_id(category_id));
    }
    


}
