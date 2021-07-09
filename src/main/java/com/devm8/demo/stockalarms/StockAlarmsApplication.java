package com.devm8.demo.stockalarms;

import com.devm8.demo.stockalarms.services.PollingJob1;
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
	public CommandLineRunner onStart(PollingJob1 poller) {
		return (args) -> {
			ScheduledExecutorService schedular = Executors.newScheduledThreadPool(1);
			//specify the time duration

			schedular.scheduleAtFixedRate(poller, 0,interval, TimeUnit.MINUTES);

		};
	}

}
