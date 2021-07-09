package com.devm8.demo.stockalarms.repo;

import com.devm8.demo.stockalarms.entities.Alarm;
import com.devm8.demo.stockalarms.entities.Stock;
import com.devm8.demo.stockalarms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRepo extends JpaRepository<Alarm, Integer> {
    Optional<Alarm> findByUserAndStock(User user, Stock stock);
    List<Alarm> findByUser(User user);
    List<Alarm> findByStockAndIsActive(Stock stock, Boolean isActive);
}
