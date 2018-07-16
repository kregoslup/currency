package com.currency.scraper.configuration;

import com.currency.scraper.exception.ScraperException;
import net.jodah.failsafe.RetryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class RetryPolicyConfiguration {
    @Bean
    public RetryPolicy materializationRetryPolicy(
            @Value("${retry.max.tries}") int retryMaxTries,
            @Value("${retry.sleep.interval}") long retrySleepInterval
    ) {
        return new RetryPolicy()
                .retryOn(ScraperException.class)
                .withDelay(retrySleepInterval, TimeUnit.SECONDS)
                .withMaxRetries(retryMaxTries);
    }
}
