package com.devm8.demo.stockalarms.services;

import com.devm8.demo.stockalarms.dto.AlarmCreateDTO;
import com.devm8.demo.stockalarms.dto.AlarmViewDTO;
import com.devm8.demo.stockalarms.entities.Alarm;
import com.devm8.demo.stockalarms.entities.Stock;
import com.devm8.demo.stockalarms.entities.User;
import com.devm8.demo.stockalarms.exceptions.AlarmNotFoundException;
import com.devm8.demo.stockalarms.exceptions.StockNotFoundException;
import com.devm8.demo.stockalarms.exceptions.UserNotFoundException;
import com.devm8.demo.stockalarms.repo.AlarmRepo;
import com.devm8.demo.stockalarms.repo.StockRepo;
import com.devm8.demo.stockalarms.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepo alarmRepo;
    private final StockRepo stockRepo;
    private final UserRepo userRepo;
    private final JavaMailSender javaMailSender;

    public boolean stockHasAlarm(String username, String symbol){
        // verify if user has an alarm for that stock and if it's active
        // this method should be called to enable/disable the create-alarm button for a stock from the stock table
        User user = userRepo.getById(username);
        Stock stock = stockRepo.getById(symbol);
        Optional<Alarm> alarm = alarmRepo.findByUserAndStock(user, stock);
        return alarm.isPresent() && alarm.get().getIsActive();
    }

    public String addAlarm(AlarmCreateDTO dto){
        //when this method is called, it is already verified that the user doesn't have another alarm for this stock
        Alarm alarm = new Alarm();
        alarm.setUser(userRepo.findById(dto.getUsername()).orElseThrow(UserNotFoundException::new));
        alarm.setStock(stockRepo.findById(dto.getSymbol()).orElseThrow(StockNotFoundException::new));
        alarm.setTargetedPercentage(dto.getTargetedPercentage());
        alarm.setIsActive(true);
        Float currentPrice = stockRepo.findById(dto.getSymbol()).orElseThrow(StockNotFoundException::new).getPrice();
        alarm.setInitialPrice(currentPrice);
        // calculated the targeted price based on the percentage, for easier comparisons in the future
        alarm.setTargetedPrice(currentPrice+dto.targetedPercentage/100f*currentPrice);

        alarmRepo.save(alarm);
        return "Alarm created.";
    }

    public List<AlarmViewDTO> viewAllAlarms(String username){
        User user = userRepo.findById(username).orElseThrow(UserNotFoundException::new);
        List<Alarm> alarmList = alarmRepo.findByUser(user);
        List<AlarmViewDTO> alarmViewDTOList = new ArrayList<>();
        if(!alarmList.isEmpty()){
            alarmViewDTOList = alarmList
                    .stream()
                    .map(alarm -> AlarmViewDTO.entityToDTO(alarm, alarm.getStock().getPrice(), alarm.getStock().getSymbol()))
                    .collect(Collectors.toList());
        }
        return alarmViewDTOList;
    }

    public void deleteAlarm(Integer id){
        Alarm alarm = alarmRepo.findById(id).orElseThrow(AlarmNotFoundException::new);
        alarmRepo.delete(alarm);
    }

    public String editAlarm(Integer id, Integer targetedPercentage){
        // I assumed the user can only edit the targeted percentage (the initial price remains the same)
        Alarm alarm = alarmRepo.findById(id).orElseThrow(AlarmNotFoundException::new);
        alarm.setTargetedPercentage(targetedPercentage);
        Float newTargetedPrice = alarm.getInitialPrice()+targetedPercentage/100f*alarm.getInitialPrice();
        alarm.setTargetedPrice(newTargetedPrice);
        alarmRepo.save(alarm);
        return "Alarm modified.";
    }

    public void sendEmail(String emailAddress, String symbol) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setSubject("Stock Alarm Application");
        message.setText("Dear Sir/Madam, \n\nThe stock " +symbol+" reached your targeted value! \n\n" +
                "Happy stonks buying! \nTeam Devm8 \n" + "https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstley");

        javaMailSender.send(message);

    }
}
