package com.currency.scraper.configuration;

import net.jodah.failsafe.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

public class CircuitBreakerConfiguration {
    @Bean
    public CircuitBreaker circuitBreakerConfiguration(
            @Value("${circuit.breaker.failure.threshold}") int circuitBreakerFailureThreshold,
            @Value("${circuit.breaker.delay}") int circuitBreakerDelay
    ) {
        return new CircuitBreaker()
                .withFailureThreshold(circuitBreakerFailureThreshold)
                .withDelay(circuitBreakerDelay, TimeUnit.SECONDS);
    }
}
