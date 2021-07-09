package com.devm8.demo.stockalarms.controllers;

import com.devm8.demo.stockalarms.dto.UserDTO;
import com.devm8.demo.stockalarms.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute(value = "userDTO") UserDTO userDTO,  Model model){
        model.addAttribute(userDTO);
        if(userService.registerUser(userDTO)){
            return "confirm-register";
        }
        else{
            return "account-already-exists";
        }

    }


}
