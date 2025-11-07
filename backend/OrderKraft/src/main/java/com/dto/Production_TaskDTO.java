package com.dto;
import java.time.LocalTime;

public class Production_TaskDTO {
    
    private long taskId;         
    private String name;
    private String description;
    private LocalTime startTime;  
    private LocalTime endTime;   
    private String status;
    private long scheduleId;     

    public Production_TaskDTO() {}

    public Production_TaskDTO(long taskId, String name, String description, LocalTime startTime, 
                             LocalTime endTime, String status, long scheduleId) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.scheduleId = scheduleId;
    }

    // Getters and Setters - update to camelCase
    public long getTaskId() { return taskId; }
    public void setTaskId(long taskId) { this.taskId = taskId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public long getScheduleId() { return scheduleId; }
    public void setScheduleId(long scheduleId) { this.scheduleId = scheduleId; }
}