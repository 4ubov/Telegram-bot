package com.chubov.SpringTelegramBot.controllers;


import com.chubov.SpringTelegramBot.DTO.UserDTO;
import com.chubov.SpringTelegramBot.JWT.JwtTokenProvider;
import com.chubov.SpringTelegramBot.services.MenuService;
import com.chubov.SpringTelegramBot.services.RestaurantService;
import com.chubov.SpringTelegramBot.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    private final RestaurantService restaurantService;

    private final MenuService menuService;

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AdminController(ModelMapper modelMapper, UserService userService, RestaurantService restaurantService, MenuService menuService, JwtTokenProvider jwtTokenProvider) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.menuService = menuService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //  Установка роли Customer
    //  Throws EntityNotFoundException
    @PutMapping("/set-employee")
    @ResponseStatus(HttpStatus.OK)
    public void setEmployee(@RequestBody UserDTO request) {
        userService.setEmployee(request);
    }

    //  Создание категориии товаров в меню
    @PostMapping("/create-menu-category")
    public void createCategory() {

    }

    //  Создание товара в меню
    @PostMapping("/create-menu-product")
    public void createProduct() {

    }

    //  Создание промокода
    @PostMapping("/create-promo-code")
    public void createPromoCode() {

    }

    //  Отправка сообщения всем клиентам (Рассылка)
    //  Настроить сущьность сообщения (Картинка текст и прочее..)
    //  Эта функция общается с telegram api/ callback-message / etc
    @PostMapping("/send-message-to-all-chat")
    public void sendMessageToAllChat(@RequestBody String text) {

    }

    //  Получить отчёт о продажах
    @GetMapping("/get-reports")
    public String getReport() {
        return "Report";
    }

    //  Получить количество пользователей бота (ROLE: USER)
    @GetMapping("/get-count-users")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Integer> getCountUsers(HttpServletRequest request) {
        if (!jwtTokenProvider.isValidAndAdmin(request)) {
            throw new BadRequestException("Invalid token, or you're dont have access");
        }
        Map<String, Integer> response = new HashMap<>();
        response.put("count", userService.getCountUsers());
        return response;
    }

    //  Получить всех пользователей (ADMIN, CUSTOMER, USER)
    @GetMapping("/get-all-users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUser();
    }


}
