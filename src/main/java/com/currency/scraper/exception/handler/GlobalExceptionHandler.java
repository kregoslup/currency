package com.currency.scraper.exception.handler;

import com.currency.scraper.response.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private ExceptionResponse handle(HttpServletRequest request, IllegalArgumentException ex) {
        return ExceptionResponse.of(ex);
    }
}
