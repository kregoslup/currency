package com.currency.scraper.vo;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.net.URI;

@Data
public class ExchangeLink {
    private URI link;

    private ExchangeLink(String s) {
        link = URI.create(s);
    }

    public static ExchangeLink of(String s) {
        if (StringUtils.isEmpty(s)) {
            throw new IllegalArgumentException("Exchange link cannot be null");
        }

        return new ExchangeLink(s);
    }
}
