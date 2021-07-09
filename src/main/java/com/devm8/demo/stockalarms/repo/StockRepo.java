package com.devm8.demo.stockalarms.repo;

import com.devm8.demo.stockalarms.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StockRepo extends JpaRepository<Stock, String> {
}
