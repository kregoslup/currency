package com.currency.scraper.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;
import java.net.URI;

@Configuration
@ConfigurationProperties(prefix = "scraper")
@Getter
public class ScraperProperties {
    @NotNull
    private URI baseAddress;

    @NotNull
    private URI rssAddress;

    @NotNull
    private int scraperPoolSize;
}
