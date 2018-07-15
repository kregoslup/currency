package com.currency.scraper.scraping;

import com.currency.scraper.entity.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class Parser {

    private static final Logger log = LoggerFactory.getLogger(Parser.class);

    public List<URI> parseExchangeLinks(String payload) {

    }

    public List<ExchangeRate>
}
