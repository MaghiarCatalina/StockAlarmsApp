package com.devm8.demo.stockalarms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AVGetStocksDTO {

    public List<AVStockDTO> bestMatches;

}
