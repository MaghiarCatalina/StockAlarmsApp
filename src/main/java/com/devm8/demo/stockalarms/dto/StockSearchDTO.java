package com.devm8.demo.stockalarms.dto;

import com.devm8.demo.stockalarms.entities.Stock;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StockSearchDTO {

    /*
    This class has the same attributes as AVStockDTO but without the @JsonProperty annotation.
    This class is needed in case we don't want the json returned to look like this for example "1. symbol" : "IBM",
    but like this "symbol" : "IBM"
     */

    public String symbol;
    public String name;


    public static StockSearchDTO entityToDto(Stock stock){
        StockSearchDTO dto = new StockSearchDTO();
        dto.setSymbol(stock.getSymbol());
        dto.setName(stock.getName());
        return dto;
    }

    public static StockSearchDTO avDtoConvert(AVStockDTO avStockDTO){
        StockSearchDTO stockSearchDTO = new StockSearchDTO();
        stockSearchDTO.setSymbol(avStockDTO.getSymbol());
        stockSearchDTO.setName(avStockDTO.getName());
        return stockSearchDTO;
    }

}
