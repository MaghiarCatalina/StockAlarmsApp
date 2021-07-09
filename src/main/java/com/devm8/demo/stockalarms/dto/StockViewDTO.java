package com.devm8.demo.stockalarms.dto;

import com.devm8.demo.stockalarms.entities.Stock;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class StockViewDTO {
    public String symbol;
    public String name;
    public Float price;
    public LocalDateTime lastUpdate;

    public static StockViewDTO entityToDTO(Stock stock){
        StockViewDTO dto = new StockViewDTO();
        dto.setSymbol(stock.getSymbol());
        dto.setName(stock.getName());
        dto.setPrice(stock.getPrice());
        dto.setLastUpdate(stock.getLastUpdate());
        return dto;
    }
}
