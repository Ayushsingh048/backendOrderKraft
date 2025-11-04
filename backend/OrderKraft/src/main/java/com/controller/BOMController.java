package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dto.BOMDTO;
import com.service.BOMService;

@RestController
@RequestMapping("/api/bom")

public class BOMController {

    @Autowired
    private BOMService bomService;

    @PostMapping
    public BOMDTO createBOM(@RequestBody BOMDTO bomDTO) {
        return bomService.createBOM(bomDTO);
    }

    @GetMapping("/{id}")
    public BOMDTO getBOMById(@PathVariable Long id) {
        return bomService.getBOMById(id);
    }

    @GetMapping
    public List<BOMDTO> getAllBOMs() {
        return bomService.getAllBOMs();
    }

    @PutMapping("/{id}")
    public BOMDTO updateBOM(@PathVariable Long id, @RequestBody BOMDTO bomDTO) {
        return bomService.updateBOM(id, bomDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBOM(@PathVariable Long id) {
        bomService.deleteBOM(id);
    }
}
