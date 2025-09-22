package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	Optional<Supplier> findByUser_Id(Long userId);

}
