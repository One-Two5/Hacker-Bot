package org.example.hakerbot.tgclient;

import org.example.hakerbot.dto.TgBotResultDto;
import org.example.hakerbot.repository.UserSessionRepository;
import org.example.hakerbot.service.BirthdayGreetingService;
import org.example.hakerbot.service.imp.GameChangerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TelegramClientBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;
    private final TgGameService tgGameService;


    public TelegramClientBot(@Value("${bot.name}")String botUsername, @Value("${bot.token}")String botToken,
                             TgGameService tgGameService
    ) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.tgGameService = tgGameService;
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

        TgBotResultDto result = tgGameService.processTgMessage(chatId, messageText);

        if (result.getBirthdayGreeting() != null && !result.getBirthdayGreeting().isBlank()) {
            sendResponse(chatId, result.getBirthdayGreeting());
        }
        sendResponse(chatId, result.getGameResponse());
    }

    private void sendResponse(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
