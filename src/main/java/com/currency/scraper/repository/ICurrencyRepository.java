package com.currency.scraper.repository;

import com.currency.scraper.entity.Currency;
import com.currency.scraper.vo.Code;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICurrencyRepository extends CrudRepository<Currency, Long> {
    Optional<Currency> findByCode(Code code);
}
