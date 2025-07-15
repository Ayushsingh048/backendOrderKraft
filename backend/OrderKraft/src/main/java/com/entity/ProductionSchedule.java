package com.entity;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "production_schedule")
public class ProductionSchedule {

	
	    @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_schedule_seq")
	    @SequenceGenerator(name = "production_schedule_seq", sequenceName = "production_schedule_seq", allocationSize = 1)
	    private Long scheduleId;

	    @Column(name = "start_date")
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	    private LocalDate startDate;
	    
	    @Column(name = "end_date")
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	    private LocalDate endDate;
	    
	    @Column(name = "status")
	    private String status;
	    
	   
	    @ManyToOne
	    @JoinColumn(name = "production_manager_id")
	    private User user;

	    
	    // getters and setters 
	    
		public Long getScheduleId() {
			return scheduleId;
		}


		public void setScheduleId(Long scheduleId) {
			this.scheduleId = scheduleId;
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


		public String getStatus() {
			return status;
		}


		public void setStatus(String status) {
			this.status = status;
		}


		public User getUser() {
			return user;
		}


		public void setUser(User user) {
			this.user = user;
		}

}
