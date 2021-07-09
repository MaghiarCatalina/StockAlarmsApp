package com.devm8.demo.stockalarms.services;

import com.devm8.demo.stockalarms.config.AlphaVantageClientConfig;
import com.devm8.demo.stockalarms.dto.AVGetStockPriceDTO;
import com.devm8.demo.stockalarms.dto.AVGetStocksDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AVStocksService {

    private final WebClient alphaVantageApiClient;

    public AVGetStocksDTO getStocks(String name){
        Mono<AVGetStocksDTO> response = alphaVantageApiClient.get()
                .uri("/query?function=SYMBOL_SEARCH&keywords="+name+"&apikey="+ AlphaVantageClientConfig.API_KEY)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AVGetStocksDTO.class);

        return response.block();
    }

    public AVGetStockPriceDTO getPrice(String name){
        Mono<AVGetStockPriceDTO> response = alphaVantageApiClient.get()
                .uri("/query?function=GLOBAL_QUOTE&symbol="+name+"&apikey="+AlphaVantageClientConfig.API_KEY)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AVGetStockPriceDTO.class);
        return response.block();
    }
}
