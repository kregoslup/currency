package com.currency.scraper.repository;

import com.currency.scraper.entity.Currency;
import com.currency.scraper.entity.ExchangeRate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface IExchangeRateRepository extends CrudRepository<ExchangeRate, Long> {
    Iterable<ExchangeRate> findByTarget(Currency target);

    Iterable<ExchangeRate> findByBase(Currency target);

    Iterable<ExchangeRate> findByCreatedAtLessThan(Instant date);

    Iterable<ExchangeRate> findByCreatedAtGreaterThan(Instant date);
}
