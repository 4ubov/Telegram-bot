package com.chubov.SpringTelegramBot.services;

import com.chubov.SpringTelegramBot.models.Menu;
import com.chubov.SpringTelegramBot.repositories.MenuRepository;
import com.chubov.SpringTelegramBot.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

//    public Menu createMenu(){
//        Menu menu = new Menu();
//        menu.setName("Main menu");
//        menu.setRestaurant(restaurantRepository.findById(1).get());
//
//        return menuRepository.save(menu);
//    }
}
