package com.chubov.SpringTelegramBot.controllers;

import com.chubov.SpringTelegramBot.JWT.JwtTokenProvider;
import com.chubov.SpringTelegramBot.models.User;
import com.chubov.SpringTelegramBot.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PostMapping("/api/get-role")
    public String getRole(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
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
