package com.currency.scraper.controller;

import com.currency.scraper.entity.ExchangeRate;
import com.currency.scraper.repository.IExchangeRateRepository;
import com.currency.scraper.response.ExchangeRateResponse;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ExchangeRateController {

    private final ModelMapper modelMapper;

    private final IExchangeRateRepository exchangeRateRepository;

    public ExchangeRateController(ModelMapper modelMapper, IExchangeRateRepository exchangeRateRepository) {
        this.modelMapper = modelMapper;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @GetMapping("/exchange-rates")
    public List<ExchangeRateResponse> fetchExchangeRates(
            @RequestParam(value = "from") Optional<Instant> from
    ) {
        Iterable<ExchangeRate> rates = from.map(exchangeRateRepository::findByCreatedAtGreaterThan)
                .orElseGet(exchangeRateRepository::findAll);
        return StreamSupport.stream(rates.spliterator(), false)
                .map(rate -> modelMapper.map(rate, ExchangeRateResponse.class))
                .collect(Collectors.toList());
    }
}
