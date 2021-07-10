package com.devm8.demo.stockalarms;

import com.devm8.demo.stockalarms.services.PollingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class StockAlarmsApplication {

	@Value("${polling.interval}")
	Integer interval;

	public static void main(String[] args) {
		SpringApplication.run(StockAlarmsApplication.class, args);
	}

	@Bean
	public CommandLineRunner onStart(PollingService poller) {
		return (args) -> {
			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
			//polling interval can be modified from application properties
			scheduler.scheduleAtFixedRate(poller, 0,interval, TimeUnit.MINUTES);

		};
	}

}
