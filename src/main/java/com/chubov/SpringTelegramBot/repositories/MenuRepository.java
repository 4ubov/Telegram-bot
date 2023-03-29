package com.chubov.SpringTelegramBot.repositories;

import com.chubov.SpringTelegramBot.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
