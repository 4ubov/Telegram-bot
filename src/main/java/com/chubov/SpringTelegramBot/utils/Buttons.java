package com.chubov.SpringTelegramBot.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.List;

@Component
public class Buttons {
    private static final InlineKeyboardButton ADMIN_BOT_SETTINGS = new InlineKeyboardButton("Настройки бота");

    public static InlineKeyboardMarkup inlineMarkup() {
        // Button response
        WebAppInfo webAppInfo = new WebAppInfo();
        webAppInfo.setUrl("https://pretendent.kz/main.html");
        ADMIN_BOT_SETTINGS.setWebApp(webAppInfo);
//        ADMIN_BOT_SETTINGS.setCallbackData("Вы нажали на кнопу - Настройки бота! ");

        List<InlineKeyboardButton> rowInline = List.of(ADMIN_BOT_SETTINGS);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
