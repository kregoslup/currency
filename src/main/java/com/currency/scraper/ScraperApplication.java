package com.currency.scraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ScraperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScraperApplication.class, args);
    }
}
