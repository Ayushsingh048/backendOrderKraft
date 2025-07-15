package com.entity;

import java.time.LocalTime;
import jakarta.persistence.*;


	@Entity
	@Table(name = "production_task")
public class ProductionTask {

	    @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_task_seq")
	    @SequenceGenerator(name = "production_task_seq", sequenceName = "production_task_seq", allocationSize = 1)
	    private Long taskId;

	    @Column(name = "name")
	    private String name;

	    @Column(name = "description")
	    private String description;
	    
	    @Column(name = "start_time")
	    private LocalTime startTime;
	    
	    @Column(name = "end_time")
	    private LocalTime endTime;
	    
	    @Column(name = "status")
	    private String status;
	    
	   
	    @ManyToOne
	    @JoinColumn(name = "schedule_id")
	    private ProductionSchedule productionSchedule;

	    // getters and setters 
	    
		public Long getTaskId() {
			return taskId;
		}


		public void setTaskId(Long taskId) {
			this.taskId = taskId;
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


		public LocalTime getStartTime() {
			return startTime;
		}


		public void setStartTime(LocalTime startTime) {
			this.startTime = startTime;
		}


		public LocalTime getEndTime() {
			return endTime;
		}


		public void setEndTime(LocalTime endTime) {
			this.endTime = endTime;
		}


		public String getStatus() {
			return status;
		}


		public void setStatus(String status) {
			this.status = status;
		}


		public ProductionSchedule getProductionSchedule() {
			return productionSchedule;
		}


		public void setProductionSchedule(ProductionSchedule productionSchedule) {
			this.productionSchedule = productionSchedule;
		}		
}
	

