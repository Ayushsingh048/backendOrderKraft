package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTasks {
	
	@Autowired
    private InventoryAlertServiceImpl alertService;

    @Scheduled(fixedRate = 60000) // every 60 seconds
    public void runStockCheck() {
    	System.out.println("Stock Alerts");
        alertService.checkLowStockAndUpdateAlerts();
    }
}
