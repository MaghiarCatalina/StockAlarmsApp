package com.devm8.demo.stockalarms.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@RequiredArgsConstructor
public class User {

    @Id
    private String username;
    private String firstName;
    private String lastName;
    private String password;

    //we don't need remove operation (CascadeType.REMOVE) - it might trigger a removal in chain
    //use Set instead of List for ManyToMany - at removal, List deletes all entries with user_name=x from user_stock and adds them back after
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_stock",
            joinColumns = @JoinColumn(name = "user_username"),
            inverseJoinColumns = @JoinColumn(name = "stock_name")
    )
    private Set<Stock> stocks = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Alarm> alarmList = new ArrayList<>();

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public void addAlarm(Alarm alarm) {
        alarmList.add(alarm);
        alarm.setUser(this);
    }

    public void removeAlarm(Alarm alarm) {
        alarmList.remove(alarm);
        alarm.setUser(null);
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
        stock.getUsers().add(this);
    }

    public void removeStock(Stock stock) {
        stocks.remove(stock);
        stock.getUsers().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
