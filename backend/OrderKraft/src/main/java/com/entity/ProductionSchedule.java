package com.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class ProductionSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCHEDULE_SEQ")
	@SequenceGenerator(name = "SCHEDULE_SEQ", sequenceName = "SCHEDULE_SEQ", allocationSize = 1)
	 @Column(name = "SCHEDULE_ID")
	private Long id;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bom_id", nullable = false)
//    @JsonManagedReference
    private BOM bom;

    private int quantityToProduce;

    private LocalDate startDate;
    private LocalDate endDate;

    private String status; // e.g. "Scheduled", "In Progress", "Completed"

    @Column(name = "created_on")
    private LocalDate createdOn = LocalDate.now();
    
    // Add relationship with Production Manager
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_manager_id")
    @JsonIgnore
    private User productionManager; // Assuming you have a User entity
    
    @Column(nullable = true)
    private int productId;

    public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public User getProductionManager() {
		return productionManager;
	}

	public void setProductionManager(User productionManager) {
		this.productionManager = productionManager;
	}

	public ProductionSchedule() {}

   

    public ProductionSchedule(Long id, BOM bom, int quantityToProduce, LocalDate startDate, LocalDate endDate,
			String status, LocalDate createdOn, User productionManager, int productId) {
		super();
		this.id = id;
		this.bom = bom;
		this.quantityToProduce = quantityToProduce;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.createdOn = createdOn;
		this.productionManager = productionManager;
		this.productId = productId;
	}

	// Getters and Setters
    public Long getId() {
        return id;
    }

    public BOM getBom() {
        return bom;
    }

    public void setBom(BOM bom) {
        this.bom = bom;
    }

    public int getQuantityToProduce() {
        return quantityToProduce;
    }

    public void setQuantityToProduce(int quantityToProduce) {
        this.quantityToProduce = quantityToProduce;
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

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }
}
