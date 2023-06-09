package com.chubov.SpringTelegramBot.telegramBotStarter.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.List;

@Component
public class Buttons {
    private static final InlineKeyboardButton ADMIN_BOT_SETTINGS = new InlineKeyboardButton("Настройки бота");

    public static InlineKeyboardMarkup inlineMarkup(Long telegramId) {
        // Button response
        WebAppInfo webAppInfo = new WebAppInfo();
        String url = "https://vk.com";
        webAppInfo.setUrl(url);
        ADMIN_BOT_SETTINGS.setWebApp(webAppInfo);
//        ADMIN_BOT_SETTINGS.setCallbackData(ADMIN_BOT_SETTINGS.getUrl());

        List<InlineKeyboardButton> rowInline = List.of(ADMIN_BOT_SETTINGS);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
