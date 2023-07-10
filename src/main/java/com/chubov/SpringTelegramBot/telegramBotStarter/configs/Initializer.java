package com.chubov.SpringTelegramBot.telegramBotStarter.configs;

import com.chubov.SpringTelegramBot.services.UserService;
import com.chubov.SpringTelegramBot.telegramBotStarter.controllers.FoodTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Slf4j
//@Component
//public class Initializer {
//    final FoodTelegramBot foodTelegramBot;
//
//    public Initializer(FoodTelegramBot foodTelegramBot) {
//        this.foodTelegramBot = foodTelegramBot;
//    }
//
//    @EventListener({ContextRefreshedEvent.class})
//    public void init() {
//        try {
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//            telegramBotsApi.registerBot((LongPollingBot) foodTelegramBot);
//        } catch (TelegramApiException e) {
//            log.error(e.getMessage());
//        }
//    }
//}


@Slf4j
@Component
public class Initializer {
    final UserService userService;
    final ModelMapper modelMapper;

    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String botToken;

    @Value("${bot.name2}")
    String botName2;

    @Value("${bot.token2}")
    String botToken2;

    @Value("${bot.name3}")
    String botName3;

    @Value("${bot.token3}")
    String botToken3;

    public Initializer(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        // Создание объектов ботов
        FoodTelegramBot bot1 = new FoodTelegramBot(new BotConfig(botName, botToken), userService, modelMapper);
        FoodTelegramBot bot2 = new FoodTelegramBot(new BotConfig(botName2, botToken2), userService, modelMapper);
        FoodTelegramBot bot3 = new FoodTelegramBot(new BotConfig(botName3, botToken3), userService, modelMapper);

        // Создание ExecutorService с фиксированным количеством потоков
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // Запуск каждого бота в отдельном потоке
        executorService.execute(() -> {
            try {
                TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
                telegramBotsApi.registerBot(bot1);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        });

        executorService.execute(() -> {
            try {
                TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
                telegramBotsApi.registerBot(bot2);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        });

        executorService.execute(() -> {
            try {
                TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
                telegramBotsApi.registerBot(bot3);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        });

        // Остановка ExecutorService
        executorService.shutdown();
    }

}
