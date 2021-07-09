package com.devm8.demo.stockalarms.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StockSaveDTO {

    public String username;
    public String symbol;
    public String name;
}
