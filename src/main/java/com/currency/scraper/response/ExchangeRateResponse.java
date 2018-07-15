package com.currency.scraper.response;

import com.currency.scraper.vo.Code;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public final class ExchangeRateResponse {
    private BigDecimal rate;

    private Code base;

    private Code target;

    private Instant createdAt;
}
