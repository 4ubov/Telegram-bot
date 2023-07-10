package com.chubov.SpringTelegramBot.telegramBotStarter.configs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@PropertySource("classpath:application.properties")
public class BotConfig {

    private String botName;

    private String botToken;

}
