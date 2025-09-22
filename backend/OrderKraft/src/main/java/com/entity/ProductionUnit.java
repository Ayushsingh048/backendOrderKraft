package com.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "production_unit")
public class ProductionUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "production_unit_seq")
    @SequenceGenerator(name = "production_unit_seq", sequenceName = "production_unit_seq", allocationSize = 1)
    private Long unitId;

    @Column(name = "name")
    private String name;

    @Column(name = "capacity")
    private long capacity;

    @ManyToOne
    @JoinColumn(name = "production_manager_id")
    private User user;

    // Getters and Setters

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
