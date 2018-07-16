package com.currency.scraper.scraping;

import com.currency.scraper.entity.ExchangeRate;
import com.currency.scraper.vo.ExchangeLink;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class ScraperParserTest {

    private String validPayload;

    private String invalidPayload;

    @Before
    public void setUp() {
        this.validPayload = "";
        this.invalidPayload = "";
    }

    @Test
    public void testParserParsesExchangeLinks() {
        Parser parser = new Parser();
        List<ExchangeLink> links = parser.parseExchangeLinks(validPayload);
        assertThat(links, CoreMatchers.hasItem(ExchangeLink.of("test")));
    }

    @Test
    public void testParserParsesInvalidPayloadToEmptyList() {
        Parser parser = new Parser();
        List<ExchangeLink> links = parser.parseExchangeLinks(invalidPayload);
        assertEquals(links.size(), 0);
    }

    @Test
    public void testParserParsesExchangeRates() {
        Parser parser = new Parser();
        List<ExchangeRate> rates = parser.parseExchangeRates(validPayload);
        assertEquals(rates.size(), 2);
    }
}
