package com.currency.scraper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public RestTemplateCustomizer restTemplateCustomizer(
            @Value("${rest.template.connect.timeout}") int httpRequestConnectTimeout,
            @Value("${rest.template.read.timeout}") int httpRequestReadTimeout
    ) {
        return restTemplate -> restTemplate.setRequestFactory(clientHttpRequestFactory(
                httpRequestConnectTimeout,
                httpRequestReadTimeout
        ));
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(
            @Value("${rest.template.connect.timeout}") int httpRequestConnectTimeout,
            @Value("${rest.template.read.timeout}") int httpRequestReadTimeout
    ) {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(httpRequestConnectTimeout);
        clientHttpRequestFactory.setReadTimeout(httpRequestReadTimeout);
        return clientHttpRequestFactory;
    }
}
