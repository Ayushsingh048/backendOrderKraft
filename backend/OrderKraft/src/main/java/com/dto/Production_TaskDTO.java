package com.dto;
import java.time.LocalTime;
//import java.util.Date;
public class Production_TaskDTO {
	
	//vars
private long task_id;
private String name;
private String description;
private LocalTime start_time;
private LocalTime end_time;
private String status;
private long schedule_id;
public Production_TaskDTO(long task_id, String name, String description,LocalTime start_time, LocalTime end_time, String status,
		long schedule_id) {
	super();
	this.task_id = task_id;
	this.name = name;
	this.description = description;
	this.start_time = start_time;
	this.end_time = end_time;
	this.status = status;
	this.schedule_id = schedule_id;
}
public long getTask_id() {
	return task_id;
}
public void setTask_id(long task_id) {
	this.task_id = task_id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public LocalTime getStart_time() {
	return start_time;
}
public void setStart_time(LocalTime start_time) {
	this.start_time = start_time;
}
public LocalTime getEnd_time() {
	return end_time;
}
public void setEnd_time(LocalTime end_time) {
	this.end_time = end_time;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public long getSchedule_id() {
	return schedule_id;
}
public void setSchedule_id(long schedule_id) {
	this.schedule_id = schedule_id;
}
public Production_TaskDTO() {}



}
