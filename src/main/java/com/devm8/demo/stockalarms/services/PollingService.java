package com.devm8.demo.stockalarms.services;

import com.devm8.demo.stockalarms.entities.Alarm;
import com.devm8.demo.stockalarms.entities.Stock;
import com.devm8.demo.stockalarms.repo.AlarmRepo;
import com.devm8.demo.stockalarms.repo.StockRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PollingService implements Runnable {

    /** --First Idea--
     * Two jobs are needed:
     * 1. One that gets the current price for all stocks that have an active alarm.
     * (To optimize it even more, you can checks the last_updated field and if less than "pooling.interval"
     * minutes passes, no need to get price for that stock for now)
     * 2. One that verifies the alarms. Get all the active alarms from the alarm table. For each one, compare the
     * targeted_price field with the current price from the stock table. Call sendEmail method for alarms that reached
     * the target and mark them as inactive.
     *
     * --Later Idea--
     * It makes sense to verify the alarms only after the prices are updated. So we might not need a
     * second thread for the alarms; we can verify them on the same thread after the price updates.
     *
     * The "polling.interval" value can be modified from application.properties
     */


    @Value("${polling.interval}")
    private Integer interval;
    private final StockService stockService;
    private final AlarmService alarmService;
    private final StockRepo stockRepo;
    private final AlarmRepo alarmRepo;

    public void updatePricesAndCheckAlarms(){
        List<Stock> stocksList = stockRepo.findAll();
        if(!stocksList.isEmpty()){
            for (Stock stock: stocksList) {
                List<Alarm> alarmList = alarmRepo.findByStockAndIsActive(stock, true);
                if(!alarmList.isEmpty()){
                    Float currentPrice = stockService.getCurrentPrice(stock.getSymbol());
                    // since we already get the active alarms list here and update the price, we can also verify them
                        checkAlarms(alarmList, currentPrice, stock.getSymbol());
                }
            }
        }
    }

    public void checkAlarms(List<Alarm> alarmList, Float currentPrice, String symbol){
        for(Alarm alarm: alarmList){
            if((Integer.signum(alarm.getTargetedPercentage()) == 1 && currentPrice-alarm.getTargetedPrice() >= 0) ||
                    (Integer.signum(alarm.getTargetedPercentage()) == -1 && currentPrice-alarm.getTargetedPrice() <= 0)){
                // positive percentage (ex. +10%) AND current price is equal with or above the target
                // OR
                // negative percentage (ex. -10%) AND current price is equal with or lower than the target
                alarmService.sendEmail(alarm.getUser().getUsername(),symbol);
                alarm.setIsActive(false);
                alarmRepo.save(alarm);
            }
        }
    }


    @Override
    public void run() {
        updatePricesAndCheckAlarms();
    }

}
