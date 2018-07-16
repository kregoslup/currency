package com.currency.scraper.service;

import com.currency.scraper.configuration.ScraperProperties;
import com.currency.scraper.entity.Currency;
import com.currency.scraper.entity.ExchangeRate;
import com.currency.scraper.repository.ICurrencyRepository;
import com.currency.scraper.repository.IExchangeRateRepository;
import com.currency.scraper.scraping.Scraper;
import com.currency.scraper.vo.ParsedExchangeRate;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class ExchangeRateService {

    private static final Logger log = LoggerFactory.getLogger(ExchangeRateService.class);

    private final IExchangeRateRepository exchangeRateRepository;

    private final ICurrencyRepository currencyRepository;

    private final RetryPolicy retryPolicy;

    private final CircuitBreaker circuitBreaker;

    private final ScraperProperties properties;

    private final Scraper scraper;

    public ExchangeRateService(
            IExchangeRateRepository exchangeRateRepository,
            RetryPolicy retryPolicy,
            CircuitBreaker circuitBreaker,
            Scraper scraper,
            ScraperProperties properties,
            ICurrencyRepository currencyRepository
    ) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.circuitBreaker = circuitBreaker;
        this.retryPolicy = retryPolicy;
        this.properties = properties;
        this.scraper = scraper;
        this.currencyRepository = currencyRepository;
    }

    private void executeScraping() {
        List<ParsedExchangeRate> parsedRates = scraper.scrape();
        List<ExchangeRate> rates = parsedRates.stream().map(parsedRate -> {
            Currency base = currencyRepository.findByCode(parsedRate.getBase()).orElseGet(
                    () -> currencyRepository.save(new Currency(parsedRate.getBase()))
            );
            Currency target = currencyRepository.findByCode(parsedRate.getBase()).orElseGet(
                    () -> currencyRepository.save(new Currency(parsedRate.getTarget()))
            );

            return new ExchangeRate(parsedRate.getRate(), base, target);
        }).collect(Collectors.toList());

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
