package com.controller;

import com.dto.InventoryAlertDTO;
import com.service.InventoryAlertService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class InventoryAlertController {

	@Autowired
    private InventoryAlertService alertService;

    //Fetch only active (unresolved) alerts
    @GetMapping("/active")
    public List<InventoryAlertDTO> getActiveAlerts() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String roleName = auth.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);
    	String cleanRole = roleName != null ? roleName.replace("ROLE_", "") : null;
    	if(cleanRole.equalsIgnoreCase("Production_Manager")) return alertService.getActiveAlertsForProduct();
    	else return alertService.getActiveAlerts();
    }

    //Manually resolve an alert(Optional)
    @PutMapping("/{id}/resolve")
    public String resolveAlert(@PathVariable Long id) {
        alertService.resolveAlert(id);
        return "Alert resolved successfully";
    }
}
