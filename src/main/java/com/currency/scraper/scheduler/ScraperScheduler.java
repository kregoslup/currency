package com.currency.scraper.scheduler;

import com.currency.scraper.service.ExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
public class ScraperScheduler {

    private static final Logger log = LoggerFactory.getLogger(ScraperScheduler.class);

    private final ExchangeRateService rateService;

    public ScraperScheduler(ExchangeRateService rateService) {
        this.rateService = rateService;
    }

    @Scheduled(fixedDelayString = "${scraper.scheduler}")
    public void scrape() {
        log.debug("Scheduling scraping");
        StopWatch watch = new StopWatch();
        watch.start();
        rateService.populateRates();
        watch.stop();
        log.debug("Scraping took {} seconds", watch.getTotalTimeSeconds());
    }
}
