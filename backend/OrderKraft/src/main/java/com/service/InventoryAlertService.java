package com.service;

import com.entity.Inventory_alert;

import java.util.List;

public interface InventoryAlertService {
		
		// Check product & raw material inventories, and create/update alerts if stock is low
		void checkLowStockAndUpdateAlerts();
		
		// Fetch all active (unresolved) alerts
		List<Inventory_alert> getActiveAlerts();
		
		// Resolve an alert manually
		void resolveAlert(Long id);
}
