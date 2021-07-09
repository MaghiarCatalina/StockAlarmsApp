package com.devm8.demo.stockalarms.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AlarmCreateDTO {

    public Integer targetedPercentage;
    public String username;
    public String symbol;
}
