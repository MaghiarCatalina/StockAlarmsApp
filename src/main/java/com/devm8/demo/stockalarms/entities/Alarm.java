package com.devm8.demo.stockalarms.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Float initialPrice;
    private Float targetedPrice;
    private Integer targetedPercentage; // +10, -20, etc.
    private Boolean isActive;

    //this relationship is bidirectional - the user cares about his stock list
    @ManyToOne
    private User user;

    //this relationship is unidirectional - the stock doesn't care about the alarms set on it
    @ManyToOne
    @JoinColumn(name = "stock_symbol")
    private Stock stock;
}
