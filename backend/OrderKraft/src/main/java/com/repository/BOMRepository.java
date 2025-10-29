package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.BOM;

public interface BOMRepository extends JpaRepository<BOM, Long> {
	BOM findByName(String code);
}
