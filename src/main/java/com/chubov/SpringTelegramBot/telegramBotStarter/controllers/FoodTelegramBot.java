package com.chubov.SpringTelegramBot.telegramBotStarter.controllers;

import com.chubov.SpringTelegramBot.DTO.UserDTO;
import com.chubov.SpringTelegramBot.telegramBotStarter.configs.BotConfig;
import com.chubov.SpringTelegramBot.models.User;
import com.chubov.SpringTelegramBot.services.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;

@Slf4j
@Service
@Transactional
public class FoodTelegramBot extends TelegramLongPollingBot {
    final BotConfig botConfig;
    final UserService userService;

    final ModelMapper modelMapper;


    @Autowired
    public FoodTelegramBot(BotConfig botConfig, UserService userService, ModelMapper modelMapper) {
        this.botConfig = botConfig;
        this.userService = userService;
        this.modelMapper = modelMapper;
        try {
            this.execute(new SetMyCommands(Collections.singletonList(new BotCommand("/start", "start bot")), new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        long chatId;
        String receivedMessage;
        Long userId;
        String username;
        String firstName;

        if (update.hasMessage()) {
            //  Id чата
            chatId = update.getMessage().getChatId();
            //  Username пользователя @Test, без собаки
            username = update.getMessage().getFrom().getUserName();
            //  Имя пользователя
            firstName = update.getMessage().getFrom().getFirstName();
            //  Id telegram пользователя
            userId = update.getMessage().getFrom().getId();

            //  Init Session
            if (update.getMessage().hasText()) {
                //  Текст сообщения в чате
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(receivedMessage, chatId, userId, username, firstName);
            }

            //если нажата одна из кнопок бота
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            firstName = update.getCallbackQuery().getFrom().getFirstName();
            username = update.getCallbackQuery().getFrom().getUserName();
            receivedMessage = update.getCallbackQuery().getData();
            botAnswerUtils(receivedMessage, chatId, userId, username, firstName);
        }
    }

    private void botAnswerUtils(String receivedMessage, long chatId, Long userId, String username, String firstName) {
        //  Save new user, or update user data, or do nothing
        User user = userService.saveNewUser(userId, username, firstName);
        //  Get user role
        String role = user.getRoles().stream().findAny().get().getRoleName();

        //  Do different response by dependency of user role
        switch (role) {
            //  ADMIN functional
            case "ADMIN":
                switch (receivedMessage) {
                    case "/start":
                        SendMessage messageResponse = userService.startBot(receivedMessage, chatId, userId, username, firstName);
                        sendMessageReply(messageResponse);
                        break;
                    case "/something":
                        // Do something
                        break;
                    default:
                        SendMessage message = new SendMessage();
                        message.setChatId(chatId);
                        message.setText(receivedMessage);
                        sendMessageReply(message);
                        break;
                }
                break;
            //  CUSTOMER functional
            case "CUSTOMER":
                break;
            //  USER functional
            case "USER":
                break;
        }
    }

    //  Execute message
    private void sendMessageReply(SendMessage message) {
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }


    //  ModelMapper methods. Converters.
    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
