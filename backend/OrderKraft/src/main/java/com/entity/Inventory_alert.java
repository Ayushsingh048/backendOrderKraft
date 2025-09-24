package com.entity;

import java.time.LocalDateTime;
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
@Table(name = "InventoryAlert")
public class Inventory_alert {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alert_seq")
	    @SequenceGenerator(name = "alert_seq", sequenceName = "alert_seq", allocationSize = 1)
	    private Long alert_id;

	    @ManyToOne
	    @JoinColumn(name = "product_id")
	    private Product product;

	    @Column(name = "alert_type")
	    private String alert_type;

	    @Column(name = "trigger_date")
	    private LocalDateTime trigger_date;
	    
	    @Column(name = "resolved", nullable = false)
	    private boolean resolved = false;

		public Long getAlert_id() {
			return alert_id;
		}

		public void setAlert_id(Long alert_id) {
			this.alert_id = alert_id;
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		public String getAlert_type() {
			return alert_type;
		}

		public void setAlert_type(String alert_type) {
			this.alert_type = alert_type;
		}

		public LocalDateTime getTrigger_date() {
			return trigger_date;
		}

		public void setTrigger_date(LocalDateTime trigger_date) {
			this.trigger_date = trigger_date;
		}

		public boolean isResolved() {
			return resolved;
		}

		public void setResolved(boolean resolved) {
			this.resolved = resolved;
		}
		
		
	}


