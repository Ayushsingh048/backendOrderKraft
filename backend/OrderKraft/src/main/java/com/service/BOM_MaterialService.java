package com.service;

import java.util.List;
import com.dto.BOMMaterialDTO;

public interface BOM_MaterialService {
    BOMMaterialDTO createMaterial(BOMMaterialDTO materialDTO);
    BOMMaterialDTO getMaterialById(Long id);
    List<BOMMaterialDTO> getAllMaterials();
    BOMMaterialDTO updateMaterial(Long id, BOMMaterialDTO materialDTO);
    void deleteMaterial(Long id);
}
