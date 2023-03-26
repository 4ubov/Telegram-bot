package com.chubov.SpringTelegramBot.controllers;


import com.chubov.SpringTelegramBot.JWT.JwtTokenProvider;
import com.chubov.SpringTelegramBot.models.User;
import com.chubov.SpringTelegramBot.services.UserDetailsServiceImpl;
import com.chubov.SpringTelegramBot.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
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
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> getToken(@RequestBody Long telegramId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(telegramId));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        Map<String, String> response = new HashMap<>();
        response.put("token", jwtTokenProvider.generateToken(authentication));
        return response;
    }

    @GetMapping("/api/get-role")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> getRole(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Long telegramId = jwtTokenProvider.getTelegramIdFromToken(token);
        assert telegramId != null;
        Optional<User> user = userService.getUser(telegramId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException();
        }
        String role = user.get().getRoles().stream().findAny().get().getRoleName();

        Map<String, String> response = new HashMap<>();
        response.put("role", role);
        return response;
    }
}
