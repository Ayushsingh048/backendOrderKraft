package com.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

@Service
public class ScheduledTasks {
	
	@Autowired
    private InventoryAlertServiceImpl alertService;

    
    @Scheduled(fixedRate = 60000) // every 60 seconds
    public void runStockCheck() {
    	System.out.println("Stock Alerts...");
    	
    	// List of Hibernate loggers that print SQL + bindings
        List<String> loggers = Arrays.asList(
            "org.hibernate.SQL",
            "org.hibernate.orm.jdbc.bind",
            "org.hibernate.type.descriptor.sql"
        );

        // Save current levels
        List<Level> prevLevels = loggers.stream()
            .map(name -> ((Logger) LoggerFactory.getLogger(name)).getLevel())
            .toList();

        try {
            // Disable all SQL-related logs
            loggers.forEach(name -> ((Logger) LoggerFactory.getLogger(name)).setLevel(Level.OFF));

            // 👉 Call your service
            alertService.checkLowStockAndUpdateAlerts();

        } finally {
            // Restore old levels
            for (int i = 0; i < loggers.size(); i++) {
                ((Logger) LoggerFactory.getLogger(loggers.get(i))).setLevel(prevLevels.get(i));
            }
        }
    }
}
