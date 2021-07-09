package com.devm8.demo.stockalarms.services;

import com.devm8.demo.stockalarms.dto.*;
import com.devm8.demo.stockalarms.entities.Stock;
import com.devm8.demo.stockalarms.entities.User;
import com.devm8.demo.stockalarms.exceptions.StockNotFoundException;
import com.devm8.demo.stockalarms.exceptions.UserNotFoundException;
import com.devm8.demo.stockalarms.repo.StockRepo;
import com.devm8.demo.stockalarms.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StockService {

    private final AVStocksService avStocksService;
    private final StockRepo stockRepo;
    private final UserRepo userRepo;

    public List<StockSearchDTO> searchStockByName(String name){
        List<StockSearchDTO> stocksFound = new ArrayList<>();
        Optional<Stock> stock = stockRepo.findById(name);
        if(stock.isPresent()){
            //convert in dto and return
            StockSearchDTO dto = StockSearchDTO.entityToDto(stock.get());
            stocksFound.add(dto);
        }
        else {
            //call AV API for search and return list
             stocksFound = avStocksService.getStocks(name).getBestMatches()
                     .stream()
                     .map(StockSearchDTO::avDtoConvert)
                     .collect(Collectors.toList());
        }
        return stocksFound;
    }


    public void addStockToMonitor(StockSaveDTO dto){
        User user = userRepo.findById(dto.getUsername()).orElseThrow(UserNotFoundException::new);
        Optional<Stock> stock = stockRepo.findById(dto.getSymbol());
        if(stock.isPresent()){
            // just add it to user's stocks
            user.addStock(stock.get());
        }
        else{
            // get price from AV API
            // save it in stock DB table
            // add it to user's stocks
            AVGetStockPriceDTO avGetStockPriceDTO = avStocksService.getPrice(dto.getSymbol());
            Stock newStock = new Stock(dto.getSymbol(), dto.getName(),
                    avGetStockPriceDTO.getStockPriceDTO().getPrice(), LocalDateTime.now());
            Stock savedStock = stockRepo.save(newStock);
            user.addStock(savedStock);
        }
    }

    public List<StockViewDTO> viewAllStocks(String username){
        User user = userRepo.findById(username).orElseThrow(UserNotFoundException::new);
        return user.getStocks()
                .stream()
                .map(StockViewDTO::entityToDTO)
                .collect(Collectors.toList());
    }

    public Float getCurrentPrice(String symbol) {
        // call AV API to get current price
        // update stock price and last_updated fields in DB
        // return current price for front end
        Float currentPrice = avStocksService.getPrice(symbol).getStockPriceDTO().getPrice();
        Stock stock = stockRepo.findById(symbol).orElseThrow(StockNotFoundException::new);
        stock.setPrice(currentPrice);
        stock.setLastUpdate(LocalDateTime.now());
        stockRepo.save(stock);
        return currentPrice;
    }
}
