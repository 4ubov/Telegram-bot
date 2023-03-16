package com.chubov.SpringTelegramBot.services;

import com.chubov.SpringTelegramBot.models.Role;
import com.chubov.SpringTelegramBot.models.User;
import com.chubov.SpringTelegramBot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String telegramId) throws EntityNotFoundException {
        // Retrieve the user from the database using their id
        User user = userRepository.findByTelegramId(Long.parseLong(telegramId))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Create a UserDetails object using the user's information
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        return new org.springframework.security.core.userdetails.User(
                String.valueOf(user.getTelegramId()),
                "",
                authorities
        );
    }
}
