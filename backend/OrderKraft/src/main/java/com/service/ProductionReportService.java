package com.service;

import java.time.LocalDate;
import java.util.List;
import com.dto.ProductionReportDTO;

public interface ProductionReportService {
    List<ProductionReportDTO> generateReport(LocalDate startDate, LocalDate endDate, String status);
}
