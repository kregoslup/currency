package com.currency.scraper.response;

import lombok.Data;

import java.time.Instant;

@Data
public final class ExceptionResponse {
    private Instant date = Instant.now();

    private String message;

    private String error;

    private ExceptionResponse(String message, String error) {
        this.message = message;
        this.error = error;
    }

    public static ExceptionResponse of(Exception ex) {
        return new ExceptionResponse(ex.getMessage(), ex.getClass().getSimpleName());
    }
}
