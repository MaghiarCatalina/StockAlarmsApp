package com.devm8.demo.stockalarms.dto;

import com.devm8.demo.stockalarms.entities.Stock;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AVStockPriceDTO {

    @JsonProperty("01. symbol")
    public String symbol;
    @JsonProperty("05. price")
    public Float price;

//    public static Stock dtoToEntity(AVStockPriceDTO dto){
//        Stock stock = new Stock();
//        stock.setSymbol(dto.getSymbol());
//        stock.setPrice(dto.getPrice());
//        return stock;
//    }
//
//    public static AVStockPriceDTO entityToDto(Stock stock){
//        AVStockPriceDTO dto = new AVStockPriceDTO();
//        dto.setSymbol(stock.getSymbol());
//        dto.setPrice(stock.getPrice());
//        return dto;
//    }

}
