package com.chubov.SpringTelegramBot.telegramBotStarter.configs;

import com.chubov.SpringTelegramBot.telegramBotStarter.controllers.FoodTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class Initializer {
    final FoodTelegramBot foodTelegramBot;

    public Initializer(FoodTelegramBot foodTelegramBot) {
        this.foodTelegramBot = foodTelegramBot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot((LongPollingBot) foodTelegramBot);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
