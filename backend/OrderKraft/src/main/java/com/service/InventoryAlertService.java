package com.service;

import com.entity.Inventory_alert;

import java.util.List;

public interface InventoryAlertService {
		
		//Check inventories for low stock and auto-create or resolve alerts and runs every 1 min using scheduler
		void checkLowStockAndUpdateAlerts();
		
		//Fetch all active (unresolved) alerts
		List<Inventory_alert> getActiveAlerts();
		
		//Resolve an alert manually
		void resolveAlert(Long id);
}
