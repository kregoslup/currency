package com.currency.scraper.scraping;

import com.currency.scraper.configuration.ScraperProperties;
import com.currency.scraper.entity.ExchangeRate;
import com.currency.scraper.vo.ExchangeLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Scraper {

    private Client client;

    private Parser parser;

    private ScraperProperties scraperProperties;

    private static final Logger log = LoggerFactory.getLogger(Scraper.class);

    public Scraper(Client client, ScraperProperties scraperProperties, Parser parser) {
        this.client = client;
        this.scraperProperties = scraperProperties;
        this.parser = parser;
    }

    private URI createAddress(URI endpoint) {
        return UriComponentsBuilder.fromUri(scraperProperties.getBaseAddress())
                .pathSegment(endpoint.toString())
                .build()
                .encode()
                .toUri();
    }

    private URI getExchangeRssAddress() {
        return createAddress(scraperProperties.getRssAddress());
    }

    private List<ExchangeLink> fetchExchangeLinks() {
        URI address = getExchangeRssAddress();
        return parser.parseExchangeLinks(client.fetchResource(address));
    }

    private List<ExchangeRate> fetchExchangeRates(List<ExchangeLink> links) {
        return links.stream()
                .map(link -> parser.parseExchangeRates(client.fetchResource(createAddress(link.getLink()))))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<ExchangeRate> scrape() {
        List<ExchangeLink> links = fetchExchangeLinks();
        return fetchExchangeRates(links);
    }
}
