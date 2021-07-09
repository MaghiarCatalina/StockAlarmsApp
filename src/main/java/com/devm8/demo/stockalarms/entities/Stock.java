package com.devm8.demo.stockalarms.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
public class Stock {

    @Id
    private String symbol;
    private String name;
    private Float price;
    private LocalDateTime lastUpdate;

    // bidirectional many to many relationship - user owns this relationship
    @ManyToMany(mappedBy = "stocks")
    private Set<User> users = new HashSet<>();

    public Stock(String symbol, String name, Float price, LocalDateTime lastUpdate) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return Objects.equals(symbol, stock.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
