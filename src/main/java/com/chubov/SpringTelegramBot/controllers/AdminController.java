package com.chubov.SpringTelegramBot.controllers;

import com.chubov.SpringTelegramBot.DTO.UserDTO;
import com.chubov.SpringTelegramBot.models.User;
import com.chubov.SpringTelegramBot.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public AdminController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    //  Установка роли Customer
    //  Throw EntityNotFoundException
    @PostMapping("/set-customer")
    @ResponseStatus(HttpStatus.CREATED)
    public void setCustomer(@RequestBody UserDTO request) {
        userService.setCustomer(request);
    }

    //  Создание меню/ каталога магазина
    @PostMapping("/create-menu")
    public void createMenu() {

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
    public void getReport() {

    }

    //  Получить количество пользователей бота (ROLE: USER)
    @GetMapping("/get-count-users")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Integer getCountUsers() {
        return userService.getCountUsers();
    }

    //  Получить всех пользователей (ADMIN, CUSTOMER, USER)
    @GetMapping("/get-all-users")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUser();
    }


}