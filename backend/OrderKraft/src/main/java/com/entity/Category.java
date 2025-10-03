package com.entity;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
	  @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cate_seq")
	    @SequenceGenerator(name = "cate_seq", sequenceName = "cate_seq", allocationSize = 1)
	  private Long categoryId;

	    @Column(name = "name")
	    private String name;

	    @Column(name = "description")
	    private String description;
	    
		public Long getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(Long categoryId) {
			this.categoryId = categoryId;
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
		
}