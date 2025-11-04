package com.dto;

public class ProductionProgressDTO {

    private Long scheduleId;
    private Long totalTasks;
    private Long completedTasks;
    private Double completionPercentage;

    public ProductionProgressDTO(Long scheduleId, Long totalTasks, Long completedTasks, Double completionPercentage) {
        this.scheduleId = scheduleId;
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.completionPercentage = completionPercentage;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(Long totalTasks) {
        this.totalTasks = totalTasks;
    }

    public Long getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(Long completedTasks) {
        this.completedTasks = completedTasks;
    }

    public Double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(Double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }
}






//package com.dto;
//
//public class ProductionProgressDTO {
//    private Long scheduleId;
//    private Long totalTasks;
//    private Long completedTasks;
//    private int percentage; // 0-100
//
//    public ProductionProgressDTO() {}
//
//    public ProductionProgressDTO(Long scheduleId, Long totalTasks, Long completedTasks, int percentage) {
//        this.scheduleId = scheduleId;
//        this.totalTasks = totalTasks;
//        this.completedTasks = completedTasks;
//        this.percentage = percentage;
//    }
//
//    // getters & setters
//    public Long getScheduleId() { return scheduleId; }
//    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }
//    public Long getTotalTasks() { return totalTasks; }
//    public void setTotalTasks(Long totalTasks) { this.totalTasks = totalTasks; }
//    public Long getCompletedTasks() { return completedTasks; }
//    public void setCompletedTasks(Long completedTasks) { this.completedTasks = completedTasks; }
//    public int getPercentage() { return percentage; }
//    public void setPercentage(int percentage) { this.percentage = percentage; }
//}
