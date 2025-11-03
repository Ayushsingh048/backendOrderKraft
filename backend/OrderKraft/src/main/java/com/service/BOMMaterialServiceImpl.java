package com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.BOMMaterialDTO;
import com.entity.BOM;
import com.entity.BOM_Material;
import com.entity.InventoryRawMaterial;
import com.repository.BOMRepository;
import com.repository.BOM_MaterialRepository;
//import com.repository.BOMMaterialRepository;
import com.repository.InventoryRawMaterialRepository;
//import com.service.BOMMaterialService;

@Service
@Transactional
public class BOMMaterialServiceImpl implements BOM_MaterialService {

    @Autowired
    private BOM_MaterialRepository materialRepository;

    @Autowired
    private BOMRepository bomRepository;

    @Autowired
    private InventoryRawMaterialRepository rawMaterialRepository;

    @Override
    public BOMMaterialDTO createMaterial(BOMMaterialDTO dto) {
        BOM_Material material = new BOM_Material();

        BOM bom = bomRepository.findById(dto.getBomId())
                .orElseThrow(() -> new RuntimeException("BOM not found"));
        InventoryRawMaterial raw = rawMaterialRepository.findById(dto.getRawMaterialId())
                .orElseThrow(() -> new RuntimeException("Raw Material not found"));

        material.setBom(bom);
        material.setRawmaterial(raw);
        material.setQntperunit(dto.getQntPerUnit());

        BOM_Material saved = materialRepository.save(material);
        dto.setMaterialId(saved.getMaterialId());
        return dto;
    }

    @Override
    public BOMMaterialDTO getMaterialById(Long id) {
        BOM_Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));
        return new BOMMaterialDTO(
                material.getMaterialId(),
                material.getBom().getBom_id(),
                material.getRawmaterial().getInventory_rawmaterial_id(),
                material.getQntperunit());
    }

    @Override
    public List<BOMMaterialDTO> getAllMaterials() {
        return materialRepository.findAll().stream()
                .map(m -> new BOMMaterialDTO(
                        m.getMaterialId(),
                        m.getBom().getBom_id(),
                        m.getRawmaterial().getInventory_rawmaterial_id(),
                        m.getQntperunit()))
                .collect(Collectors.toList());
    }

    @Override
    public BOMMaterialDTO updateMaterial(Long id, BOMMaterialDTO dto) {
        BOM_Material material = materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));
        material.setQntperunit(dto.getQntPerUnit());
        materialRepository.save(material);
        return dto;
    }

    @Override
    public void deleteMaterial(Long id) {
        materialRepository.deleteById(id);
    }
}
