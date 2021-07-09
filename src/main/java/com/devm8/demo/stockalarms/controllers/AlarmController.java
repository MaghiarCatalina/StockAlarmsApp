package com.devm8.demo.stockalarms.controllers;

import com.devm8.demo.stockalarms.dto.AlarmCreateDTO;
import com.devm8.demo.stockalarms.dto.AlarmViewDTO;
import com.devm8.demo.stockalarms.services.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @PostMapping
    public String createAlarm(@RequestBody AlarmCreateDTO alarmCreateDTO){
        return alarmService.addAlarm(alarmCreateDTO);
    }

    @GetMapping
    public boolean stockHasAlarm(@RequestParam String username, @RequestParam String symbol){
        return alarmService.stockHasAlarm(username,symbol);
    }

    @GetMapping("/all")
    public List<AlarmViewDTO> viewAllAlarms(@RequestParam String username){
        return alarmService.viewAllAlarms(username);
    }

    @DeleteMapping("/{id}")
    public void deleteAlarm(@PathVariable Integer id){
        alarmService.deleteAlarm(id);
    }

    @PatchMapping("/{id}")
    public String editAlarm(@PathVariable Integer id, @RequestParam Integer targetedPercentage){
        // I used PATCH instead of PUT because it's a partial update (only the modified parameter is supplied)
        return alarmService.editAlarm(id, targetedPercentage);
    }

    @PostMapping("/email-test/{username}")
    public String sendEmail(@PathVariable String username){
        alarmService.sendEmail(username, "BA");
        return "Email sent.";
    }


}
