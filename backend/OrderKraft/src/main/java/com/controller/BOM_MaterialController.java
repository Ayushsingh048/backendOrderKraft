package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dto.BOMMaterialDTO;
import com.service.BOM_MaterialService;

@RestController
@RequestMapping("/api/bom-material")

public class BOM_MaterialController {

    @Autowired
    private BOM_MaterialService materialService;

    @PostMapping
    public BOMMaterialDTO createMaterial(@RequestBody BOMMaterialDTO materialDTO) {
        return materialService.createMaterial(materialDTO);
    }

    @GetMapping("/{id}")
    public BOMMaterialDTO getMaterialById(@PathVariable Long id) {
        return materialService.getMaterialById(id);
    }

    @GetMapping
    public List<BOMMaterialDTO> getAllMaterials() {
        return materialService.getAllMaterials();
    }

    @PutMapping("/{id}")
    public BOMMaterialDTO updateMaterial(@PathVariable Long id, @RequestBody BOMMaterialDTO materialDTO) {
        return materialService.updateMaterial(id, materialDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
    }
}
