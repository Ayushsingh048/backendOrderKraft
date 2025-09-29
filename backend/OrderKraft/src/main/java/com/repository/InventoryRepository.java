package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Inventory;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    

    // find inventory row for a product (product has field product_id)
    Optional<Inventory> findByProduct_ProductId(Long productId);
    // find Inventory rows by product -> category -> categoryId
    List<Inventory> findByProductCategoryCategoryId(Long categoryId);

}
