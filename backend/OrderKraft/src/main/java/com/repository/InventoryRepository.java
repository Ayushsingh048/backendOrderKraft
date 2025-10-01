package com.repository;


import com.entity.Inventory;
import com.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface InventoryRepository extends JpaRepository<Inventory, Long> {


// Derived query: find the inventory row for a given Product entity
Optional<Inventory> findByProduct(Product product);


// existing helper
List<Inventory> findByProductCategoryCategoryId(Long categoryId);


}



//package com.repository;
//
//import com.entity.Inventory;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface InventoryRepository extends JpaRepository<Inventory, Long> {
//
//    /**
//     * Explicit JPQL query to find Inventory by the product's product_id field.
//     * Using explicit JPQL avoids Spring Data method-name parsing errors when
//     * entity property names include underscores (e.g. product_id).
//     *
//     * If your Product entity uses a different property name for the id (for example 'id'),
//     * change the JPQL to use that property (i.product.id).
//     */
//    @Query("SELECT i FROM Inventory i WHERE i.product.product_id = :pid")
//    Optional<Inventory> findByProductProductId(@Param("pid") Long pid);
//    
//    // find Inventory rows by product -> category -> categoryId
//    List<Inventory> findByProductCategoryCategoryId(Long categoryId);
//}

