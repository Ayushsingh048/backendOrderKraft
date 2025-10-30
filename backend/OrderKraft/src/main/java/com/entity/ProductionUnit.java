package com.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "production_unit")
public class ProductionUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_unit_seq")
    @SequenceGenerator(name = "production_unit_seq", sequenceName = "production_unit_seq", allocationSize = 1)
    private Long unitId;

    // Name of the production unit
    @Column(name = "name")
    private String name;

    // Capacity of the unit
    @Column(name = "capacity")
    private long capacity;

    // ID of the production manager who owns this unit
    @Column(name = "production_manager_id")
    private Long productionManagerId;

    // ID of the task currently assigned to this unit
    @Column(name = "task_id")
    private Long taskId;

    // ----------------- Getters and Setters -----------------
    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public Long getProductionManagerId() {
        return productionManagerId;
    }

    public void setProductionManagerId(Long productionManagerId) {
        this.productionManagerId = productionManagerId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
