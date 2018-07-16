package com.currency.scraper.service;

import com.currency.scraper.configuration.ScraperProperties;
import com.currency.scraper.entity.ExchangeRate;
import com.currency.scraper.repository.ICurrencyRepository;
import com.currency.scraper.repository.IExchangeRateRepository;
import com.currency.scraper.scraping.Scraper;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;

@Service
public class ExchangeRateService {

    private static final Logger log = LoggerFactory.getLogger(ExchangeRateService.class);

    private final IExchangeRateRepository exchangeRateRepository;

    private final RetryPolicy retryPolicy;

    private final CircuitBreaker circuitBreaker;

    private final ScraperProperties properties;

    private final Scraper scraper;

    public ExchangeRateService(
            IExchangeRateRepository exchangeRateRepository,
            RetryPolicy retryPolicy,
            CircuitBreaker circuitBreaker,
            Scraper scraper,
            ScraperProperties properties
    ) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.circuitBreaker = circuitBreaker;
        this.retryPolicy = retryPolicy;
        this.properties = properties;
        this.scraper = scraper;
    }

    private void executeScraping() {
        List<ExchangeRate> rates = scraper.scrape();
        exchangeRateRepository.saveAll(rates);
    }

    public void populateRates() {
        Failsafe.with(retryPolicy)
                .with(circuitBreaker)
                .with(Executors.newScheduledThreadPool(properties.getScraperPoolSize()))
                .withFallback(() -> log.debug("Failed while populating scraped data"))
                .run(this::executeScraping);

    }
}
