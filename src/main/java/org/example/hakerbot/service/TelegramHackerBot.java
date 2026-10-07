package org.example.hakerbot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramHackerBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;
    private final GameChangerService gameChangerService;


    public TelegramHackerBot(@Value("${bot.name}")String botUsername, @Value("${bot.token}")String botToken,
                             GameChangerService gameChangerService) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.gameChangerService = gameChangerService;
    }

    @Override
    public String getBotUsername() { return this.botUsername; }

    @Override
    public String getBotToken() { return this.botToken; }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String messageText = update.getMessage().getText().trim();
        Long chatId = update.getMessage().getChatId();
        String replyText = gameChangerService.handleMessage(chatId, messageText);

        sendText(chatId, replyText);
    }

    private void sendText(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
