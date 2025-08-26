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
        rawMaterial.setUnitCost(dto.getUnitCost());
        rawMaterial.setStockQuantity(dto.getStockQuantity());
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
    
    //search 
    @Override
    public List<RawMaterial> getRawMaterialsByName(String name) {
        return rawMaterialRepository.findByName(name);
    }

    @Override
    public List<RawMaterial> getRawMaterialsByUnitCost(Double unitCost) {
    	return rawMaterialRepository.findByUnitCost(unitCost);
    }

    @Override
    public List<RawMaterial> getRawMaterialsByStockQuantity(Long stockQuantity) {
    	return rawMaterialRepository.findByStockQuantity(stockQuantity);
    }

    @Override
    public List<RawMaterial> getRawMaterialsByStatus(String status) {
        return rawMaterialRepository.findByStatus(status);
    }

    @Override
    public List<RawMaterial> getRawMaterialsBySupplier(Long supplierId) {
        return rawMaterialRepository.findBySuppliers_SupplierId(supplierId);
    }
    
    //update the supplier details 
    @Override
    public RawMaterial addSupplierToRawMaterial(Long rawMaterialId, Long supplierId) {
        // Fetch raw material by ID
        RawMaterial rawMaterial = rawMaterialRepository.findById(rawMaterialId)
            .orElseThrow(() -> new RuntimeException("RawMaterial not found with ID: " + rawMaterialId));

        // Fetch supplier by ID
        Supplier supplier = supplierRepository.findById(supplierId)
            .orElseThrow(() -> new RuntimeException("Supplier not found with ID: " + supplierId));

        // Add supplier to raw material
        rawMaterial.getSuppliers().add(supplier);

        // Maintain bidirectional relation
        supplier.getRawMaterials().add(rawMaterial);

        // Save and return updated raw material
        return rawMaterialRepository.save(rawMaterial);
    }

    
    
   


}