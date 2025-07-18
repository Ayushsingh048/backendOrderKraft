package com.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.Raw_MaterialDTO;
import com.entity.RawMaterial;
import com.entity.Supplier;
import com.repository.RawMaterialRepository;
import com.repository.SupplierRepository;

@Service
public class RawMaterialServiceImpl implements RawMaterialService {

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public RawMaterial createRawMaterial(Raw_MaterialDTO dto) {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName(dto.getName());
        rawMaterial.setUnit_cost(dto.getUnit_cost());
        rawMaterial.setStock_quantity(dto.getStock_quantity());
        rawMaterial.setStatus(dto.getStatus());

        if (dto.getSupplierIds() != null && !dto.getSupplierIds().isEmpty()) {
            Set<Supplier> suppliers = new HashSet<>(supplierRepository.findAllById(dto.getSupplierIds()));
            rawMaterial.setSuppliers(suppliers);

            // Maintain bidirectional relationship
            for (Supplier supplier : suppliers) {
                supplier.getRawMaterials().add(rawMaterial);
            }
        }

        return rawMaterialRepository.save(rawMaterial);
    }

    @Override
    public RawMaterial getRawMaterialById(Long id) {
        return rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw material not found with ID: " + id));
    }

    @Override
    public List<RawMaterial> getAllRawMaterials() {
        return rawMaterialRepository.findAll();
    }


}