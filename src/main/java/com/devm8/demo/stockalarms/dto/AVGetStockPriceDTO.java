package com.devm8.demo.stockalarms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AVGetStockPriceDTO {

    @JsonProperty("Global Quote")
    public AVStockPriceDTO stockPriceDTO;
}
