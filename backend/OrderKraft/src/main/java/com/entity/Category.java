package com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
	  @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cate_seq")
	    @SequenceGenerator(name = "cate_seq", sequenceName = "cate_seq", allocationSize = 1)
	    private Long category_id;

	    @Column(name = "name")
	    private String name;

	    @Column(name = "description")
	    private String description;
	    
	    @ManyToOne
	    @JoinColumn(name = "inventory_manager_id")
	    private User inventoryManager;

		public Long getCategory_id() {
			return category_id;
		}

		public void setCategory_id(Long category_id) {
			this.category_id = category_id;
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

		public User getInventoryManager() {
			return inventoryManager;
		}

		public void setInventoryManager(User inventoryManager) {
			this.inventoryManager = inventoryManager;
		}

	
		
}