package org.example.hakerbot.config;

import org.example.hakerbot.service.HackerBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

    @Value("${bot.name}")
    private String botUserName;
    @Value("${bot.token}")
    private String botToken;


    @Bean
    public TelegramBotsApi telegramBotsApi(HackerBot hackerBot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(hackerBot);
        return botsApi;
    }
}



