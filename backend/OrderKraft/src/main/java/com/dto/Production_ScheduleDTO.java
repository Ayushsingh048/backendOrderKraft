package com.dto;

import java.time.LocalDate;

public class Production_ScheduleDTO {

    private Long bomId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int quantityToProduce;

    public Long getBomId() {
        return bomId;
    }

    public void setBomId(Long bomId) {
        this.bomId = bomId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getQuantityToProduce() {
        return quantityToProduce;
    }

    public void setQuantityToProduce(int quantityToProduce) {
        this.quantityToProduce = quantityToProduce;
    }
}
