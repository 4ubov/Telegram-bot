package com.chubov.SpringTelegramBot.services;

import com.chubov.SpringTelegramBot.models.Restaurant;
import com.chubov.SpringTelegramBot.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

//    public Restaurant createRestaurant(){
//        Restaurant restaurant = new Restaurant();
//        restaurant.setName("McDonald's");
//        restaurant.setAddress("USA LA 21st/2");
//        restaurant.setPhone("+717717177");
//
//        return restaurantRepository.save(restaurant);
//    }
}

