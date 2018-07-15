package com.currency.scraper.scraping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    private final RestTemplate client;

    public Client(RestTemplate client) {
        this.client = client;
    }

    public String fetchResource(URI resourceAddress) {
        log.debug("Fetching resource: {}", resourceAddress);
        return client.getForEntity(resourceAddress, String.class).getBody();
    }
}
