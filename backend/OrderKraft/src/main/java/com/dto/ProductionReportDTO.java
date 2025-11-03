package com.dto;

import java.time.LocalDate;
import java.util.List;

public class ProductionReportDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private String unitName;
    private long totalTasks;
    private long completedTasks;
    private double completionRate;
    private List<String> delayedTasks;

    // Constructors
    public ProductionReportDTO() {}

    public ProductionReportDTO(LocalDate startDate, LocalDate endDate, String unitName,
                               long totalTasks, long completedTasks, double completionRate, List<String> delayedTasks) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.unitName = unitName;
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.completionRate = completionRate;
        this.delayedTasks = delayedTasks;
    }

    // Getters and Setters
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getUnitName() { return unitName; }
    public void setUnitName(String unitName) { this.unitName = unitName; }

    public long getTotalTasks() { return totalTasks; }
    public void setTotalTasks(long totalTasks) { this.totalTasks = totalTasks; }

    public long getCompletedTasks() { return completedTasks; }
    public void setCompletedTasks(long completedTasks) { this.completedTasks = completedTasks; }

    public double getCompletionRate() { return completionRate; }
    public void setCompletionRate(double completionRate) { this.completionRate = completionRate; }

    public List<String> getDelayedTasks() { return delayedTasks; }
    public void setDelayedTasks(List<String> delayedTasks) { this.delayedTasks = delayedTasks; }
}
