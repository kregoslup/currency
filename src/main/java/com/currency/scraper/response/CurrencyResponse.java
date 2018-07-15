package com.currency.scraper.response;

import com.currency.scraper.vo.Code;
import lombok.Data;

@Data
public final class CurrencyResponse {
    private Code code;

    private String name;
}
