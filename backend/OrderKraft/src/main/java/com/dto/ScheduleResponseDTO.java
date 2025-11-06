package com.dto;

import java.time.LocalDate;

public class ScheduleResponseDTO {
    private Long scheduleId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private LocalDate createdOn;
    private Integer quantityToProduce;
    private Long bomId;
    private Long productionManagerId; // Add this field

    // Constructors
    public ScheduleResponseDTO() {}

    public ScheduleResponseDTO(Long scheduleId, LocalDate startDate, LocalDate endDate, 
                              String status, LocalDate createdOn, Integer quantityToProduce, 
                              Long bomId, Long productionManagerId) {
        this.scheduleId = scheduleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdOn = createdOn;
        this.quantityToProduce = quantityToProduce;
        this.bomId = bomId;
        this.productionManagerId = productionManagerId;
    }

    // Getters and Setters
    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDate getCreatedOn() { return createdOn; }
    public void setCreatedOn(LocalDate createdOn) { this.createdOn = createdOn; }
    
    public Integer getQuantityToProduce() { return quantityToProduce; }
    public void setQuantityToProduce(Integer quantityToProduce) { this.quantityToProduce = quantityToProduce; }
    
    public Long getBomId() { return bomId; }
    public void setBomId(Long bomId) { this.bomId = bomId; }
    
    public Long getProductionManagerId() { return productionManagerId; }
    public void setProductionManagerId(Long productionManagerId) { this.productionManagerId = productionManagerId; }
}