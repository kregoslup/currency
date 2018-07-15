package com.currency.scraper.entity;

import com.currency.scraper.vo.Code;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Code code;

    private String name;

    public Currency(Code code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
