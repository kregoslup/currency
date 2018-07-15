package com.currency.scraper.entity;

import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Entity
@Table(name = "currency")
public class ExchangeRate {
    @Id
    @GeneratedValue
    private Long id;

    @Column(precision = 4)
    private BigDecimal rate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "base_id")
    private Currency base;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "target_id")
    private Currency target;

    private Instant createdAt;

    public ExchangeRate(BigDecimal rate, Currency base, Currency target) {
        this.rate = rate;
        this.base = base;
        this.target = target;
    }

    @PrePersist
    public void onSave() {
        this.createdAt = Instant.now();
    }
}
