package com.chubov.SpringTelegramBot.controllers;

import com.chubov.SpringTelegramBot.JWT.JwtTokenProvider;
import com.chubov.SpringTelegramBot.models.User;
import com.chubov.SpringTelegramBot.services.UserDetailsServiceImpl;
import com.chubov.SpringTelegramBot.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UserController(ModelMapper modelMapper, UserService userService, JwtTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/api/get-token")
    public String getToken(@RequestBody Long tgId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(tgId));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        return jwtTokenProvider.generateToken(authentication);
    }

    @PostMapping("/api/get-role")
    public String getRole(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader;
        Long telegramId = JwtTokenProvider.getTelegramIdFromToken(token);
        assert telegramId != null;
        Optional<User> user = userService.getUser(telegramId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException();
        }
        String role = user.get().getRoles().stream().findAny().get().getRoleName();

        return role;
    }
}
