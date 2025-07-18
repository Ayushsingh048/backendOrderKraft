package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.RawMaterial;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {

}
