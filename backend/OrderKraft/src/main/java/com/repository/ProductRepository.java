package com.repository;

import com.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);               // Search by name (exact match)
    List<Product> findByCategory_CategoryId(Long categoryId);
    
    // search by production manager id 
    List<Product> findByProductionManager_Id(Long id);


}
