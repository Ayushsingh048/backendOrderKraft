package com.actuator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Balance;

@Component("stripeHealth")
public class StripeHealthIndicator implements HealthIndicator {
	
	@Value("${stripe.secretKey}")
	private String secretKey;
	
	@Override
	public Health health() {
		try {
            Stripe.apiKey = secretKey;
            Balance balance = Balance.retrieve();
            return Health.up().withDetail("Stripe", "Available").build();
        } catch (StripeException e) {
            return Health.down().withDetail("Stripe", "Not reachable").build();
        }
	}

}
