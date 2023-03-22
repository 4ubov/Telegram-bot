package com.chubov.SpringTelegramBot.repositories;

import com.chubov.SpringTelegramBot.models.Role;
import com.chubov.SpringTelegramBot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByTelegramId(Long userId);

    Optional<User> findByUsername(String username);
}
