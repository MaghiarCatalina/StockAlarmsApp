package com.devm8.demo.stockalarms.controllers;

import com.devm8.demo.stockalarms.dto.*;
import com.devm8.demo.stockalarms.services.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class StocksController {

    private final StockService stockService;

    @GetMapping("/home") //temporary here, for redirect after login
    public String home(){ // works only if @Controller instead of @RestController
        return "home";
    }


    @GetMapping("/{name}")
    public List<StockSearchDTO> searchStocksByName(@PathVariable String name){
        return stockService.searchStockByName(name);
    }

    @PostMapping
    public String addStock(@RequestBody StockSaveDTO dto){
        stockService.addStockToMonitor(dto);
        return "Stock added.";
    }

    @GetMapping("/all")
    public List<StockViewDTO> getAllStocks(@RequestParam String username){
        return stockService.viewAllStocks(username);
    }

    @GetMapping()
    public Float refreshPrice(@RequestParam String symbol){
        return stockService.getCurrentPrice(symbol);
    }

}
