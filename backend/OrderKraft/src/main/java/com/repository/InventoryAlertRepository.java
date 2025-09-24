package com.repository;

import com.entity.Inventory_alert;
import com.entity.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryAlertRepository extends JpaRepository<Inventory_alert, Long> {

	boolean existsByProductAndResolvedFalse(Product product);
	List<Inventory_alert> findByResolvedFalse();
	List<Inventory_alert> findByProductAndResolvedFalse(Product product);

}
