package com.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

//import java.util.Date;


public class Production_ScheduleDTO {
//vars
private long schedule_id;
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
private LocalDate start_date;
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
private LocalDate end_date;
private String status;
private  long production_manager_id;
public Production_ScheduleDTO(long schedule_id, LocalDate start_date, 	LocalDate end_date, String status,
		long production_manager_id) {
	super();
	this.schedule_id = schedule_id;
	this.start_date = start_date;
	this.end_date = end_date;
	this.status = status;
	this.production_manager_id = production_manager_id;
}
public long getSchedule_id() {
	return schedule_id;
}
public void setSchedule_id(long schedule_id) {
	this.schedule_id = schedule_id;
}
public LocalDate getStart_date() {
	return start_date;
}
public void setStart_date(LocalDate start_date) {
	this.start_date = start_date;
}
public LocalDate getEnd_date() {
	return end_date;
}
public void setEnd_date(LocalDate end_date) {
	this.end_date = end_date;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public long getProduction_manager_id() {
	return production_manager_id;
}
public void setProduction_manager_id(long production_manager_id) {
	this.production_manager_id = production_manager_id;
}
public Production_ScheduleDTO() {

}


}
