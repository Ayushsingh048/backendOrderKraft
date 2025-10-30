package com.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dto.ProductionReportDTO;
import com.service.ProductionReportService;

@RestController
@RequestMapping("/production_report")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductionReportController {

    @Autowired
    private ProductionReportService reportService;

    @GetMapping("/generate")
    public ResponseEntity<List<ProductionReportDTO>> generateReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String status) {

        return ResponseEntity.ok(reportService.generateReport(startDate, endDate, status));
    }
}
