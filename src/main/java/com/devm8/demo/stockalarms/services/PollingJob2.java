package com.devm8.demo.stockalarms.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PollingJob2 implements Runnable{

    public void checkAlarms(){

    }

    @Override
    public void run() {
        checkAlarms();
    }
}
