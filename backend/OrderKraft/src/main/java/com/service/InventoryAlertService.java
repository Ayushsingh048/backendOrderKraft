package com.service;

import com.dto.InventoryAlertDTO;
import com.entity.Inventory_alert;

import java.util.List;
import java.util.Optional;

public interface InventoryAlertService {
		
		// Check both product and raw material inventories for low stock, 
		// create new alerts if necessary, and auto-resolve existing alerts if stock is sufficient.
		void checkLowStockAndUpdateAlerts();
		
		// Fetch all active (unresolved) alerts
		List<InventoryAlertDTO> getActiveAlerts();
		
		// Resolve an alert manually
		void resolveAlert(Long id);
		
		// Fetch alert by ID
		Optional<Inventory_alert> getInventoryAlertById(Long id);

		List<InventoryAlertDTO> getActiveAlertsForProduct();

}
