package com.devm8.demo.stockalarms.services;

import com.devm8.demo.stockalarms.dto.UserDTO;
import com.devm8.demo.stockalarms.entities.User;
import com.devm8.demo.stockalarms.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findById(username)
                .map(UserService::mapUser)
                .orElseThrow(() -> new UsernameNotFoundException("The requested user was not found."));
    }

    private static UserDetails mapUser(User user){
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), list);
    }

    @Transactional
    public boolean registerUser(UserDTO dto){
        if(userRepo.findById(dto.getUsername()).isPresent()){
            return false;
        }
        else {
            String encodedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
            dto.setPassword(encodedPassword);
            userRepo.save(UserDTO.toEntity(dto));
            return true;
        }
    }

}
