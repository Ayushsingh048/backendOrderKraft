package com.repository;

import com.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /**
     * Explicit JPQL query to find Inventory by the product's product_id field.
     * Using explicit JPQL avoids Spring Data method-name parsing errors when
     * entity property names include underscores (e.g. product_id).
     *
     * If your Product entity uses a different property name for the id (for example 'id'),
     * change the JPQL to use that property (i.product.id).
     */
    @Query("SELECT i FROM Inventory i WHERE i.product.product_id = :pid")
    Optional<Inventory> findByProductProductId(@Param("pid") Long pid);
}

