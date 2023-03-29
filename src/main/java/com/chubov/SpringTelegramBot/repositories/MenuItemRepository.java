package com.chubov.SpringTelegramBot.repositories;

import com.chubov.SpringTelegramBot.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
}
