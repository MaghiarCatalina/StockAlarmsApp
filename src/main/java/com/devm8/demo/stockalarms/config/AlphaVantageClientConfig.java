package com.devm8.demo.stockalarms.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
@RequiredArgsConstructor
public class AlphaVantageClientConfig {

    public static final String API_KEY = "3P4E1RKI491TSS5O";

    @Bean
    public WebClient localApiClient() {
        return WebClient.create("https://www.alphavantage.co");
    }
}
