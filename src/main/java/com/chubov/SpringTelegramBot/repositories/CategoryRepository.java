package com.chubov.SpringTelegramBot.repositories;

import com.chubov.SpringTelegramBot.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
