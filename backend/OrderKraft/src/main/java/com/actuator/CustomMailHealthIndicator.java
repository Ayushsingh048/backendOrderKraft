package com.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component("customMailHealth")
public class CustomMailHealthIndicator implements HealthIndicator {
	
    private final JavaMailSenderImpl mailSender;

    public CustomMailHealthIndicator(JavaMailSenderImpl mailSender) {
    	System.out.println("yo! mail");
        this.mailSender = mailSender;
    }

    @Override
    public Health health() {

        try {
            mailSender.testConnection();
            return Health.up().withDetail("Mail", "SMTP reachable").build();
        } catch (Exception e) {
            return Health.down().withDetail("Mail", "SMTP not reachable").build();
        }
    }
}
