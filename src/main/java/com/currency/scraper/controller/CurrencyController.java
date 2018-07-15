package com.currency.scraper.controller;

import com.currency.scraper.entity.Currency;
import com.currency.scraper.repository.ICurrencyRepository;
import com.currency.scraper.response.CurrencyResponse;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class CurrencyController {

    private final ModelMapper modelMapper;

    private final ICurrencyRepository currencyRepository;

    public CurrencyController(ModelMapper modelMapper, ICurrencyRepository currencyRepository) {
        this.modelMapper = modelMapper;
        this.currencyRepository = currencyRepository;
    }

    @GetMapping("/currencies")
    public List<CurrencyResponse> fetchCurrencies() {
        Iterable<Currency> currencies = currencyRepository.findAll();
        return StreamSupport.stream(currencies.spliterator(), false)
                .map(currency -> modelMapper.map(currency, CurrencyResponse.class))
                .collect(Collectors.toList());
    }
}
