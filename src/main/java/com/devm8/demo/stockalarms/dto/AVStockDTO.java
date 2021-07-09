package com.devm8.demo.stockalarms.dto;

import com.devm8.demo.stockalarms.entities.Stock;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AVStockDTO {

    @JsonProperty("1. symbol")
    public String symbol;
    @JsonProperty("2. name")
    public String name;

}
