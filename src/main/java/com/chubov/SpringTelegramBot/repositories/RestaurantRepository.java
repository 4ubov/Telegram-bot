package com.chubov.SpringTelegramBot.repositories;

import com.chubov.SpringTelegramBot.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
