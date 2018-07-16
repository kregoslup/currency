package com.currency.scraper.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ParsedExchangeRate {
    private BigDecimal rate;

    private Code base;

    private Code target;

    private ParsedExchangeRate(BigDecimal rate, Code base, Code target) {
        this.rate = rate;
        this.base = base;
        this.target = target;
    }

    public static ParsedExchangeRate of(String rate, String base, String target) {
        return new ParsedExchangeRate(
                BigDecimal.valueOf(Long.valueOf(rate)),
                Code.of(base),
                Code.of(target)
        );
    }
}
