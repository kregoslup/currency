package com.currency.scraper.configuration;

import com.currency.scraper.entity.Currency;
import com.currency.scraper.vo.Code;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class ModelMapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new AbstractConverter<Currency, Code>() {

            @Override
            protected Code convert(Currency source) {
                return Optional.ofNullable(source).map(Currency::getCode).orElse(null);
            }
        });

        return modelMapper;
    }
}
