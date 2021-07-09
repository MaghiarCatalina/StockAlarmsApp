package com.devm8.demo.stockalarms.services;

import com.devm8.demo.stockalarms.entities.Alarm;
import com.devm8.demo.stockalarms.entities.Stock;
import com.devm8.demo.stockalarms.repo.AlarmRepo;
import com.devm8.demo.stockalarms.repo.StockRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PollingJob1 implements Runnable {

    /** TODO:
     * Two jobs are needed:
     * 1. One that gets the current price for all stocks that have an active alarm.
     * (To optimize it even more, you can checks the last_updated field and if less than "pooling.interval"
     * minutes passes, no need to get price for that stock for now)
     * 2. One that verifies the alarms. Get all the active alarms from the alarm table. For each one, compare the
     * targeted_price field with the current price from the stock table. Call sendEmail method for alarms that reached
     * the target and mark them as inactive.
     *
     * The "polling.interval" value can be modified from application.properties
     */


    @Value("${polling.interval}")
    private Integer interval;
    private final StockService stockService;
    private final StockRepo stockRepo;
    private final AlarmRepo alarmRepo;

    public void updatePriceJob(){
        List<Stock> stocksList = stockRepo.findAll();
        if(!stocksList.isEmpty()){
            for (Stock stock: stocksList) {
                List<Alarm> alarmList = alarmRepo.findByStockAndIsActive(stock, true);
                if(!alarmList.isEmpty()){
                    stockService.getCurrentPrice(stock.getSymbol());
                }
            }
        }
    }


    @Override
    public void run() {
        updatePriceJob();
    }

}
