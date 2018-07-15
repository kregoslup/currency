package com.currency.scraper.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Code {
    @Column(nullable = false, name = "code")
    @JsonValue
    private String value;

    private Code(String value) {
        this.value = value;
    }

    public Code() {}

    public static Code of(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("Code cannot be null or empty");
        }

        return new Code(value);
    }
}
