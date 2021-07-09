package com.devm8.demo.stockalarms.dto;

import com.devm8.demo.stockalarms.entities.Alarm;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AlarmViewDTO {

    public Integer id;
    public String symbol;
    public Float initialPrice;
    public Float currentPrice;
    public Integer currentPercentage;
    public Integer targetedPercentage;
    public Boolean isActive;

    public static AlarmViewDTO entityToDTO(Alarm alarm, Float currentPrice, String symbol){
        AlarmViewDTO dto = new AlarmViewDTO();
        dto.setId(alarm.getId());
        dto.setSymbol(symbol);
        dto.setInitialPrice(alarm.getInitialPrice());
        dto.setCurrentPrice(currentPrice);
        dto.setTargetedPercentage(alarm.getTargetedPercentage());
        dto.setIsActive(alarm.getIsActive());
        Integer currentPercentage =(int)((currentPrice-alarm.getInitialPrice())*100/ alarm.getInitialPrice());
        dto.setCurrentPercentage(currentPercentage);
        return dto;
    }

}
