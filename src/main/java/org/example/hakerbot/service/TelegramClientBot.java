package org.example.hakerbot.service;

import org.example.hakerbot.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;

@Service
public class TelegramClientBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;
    private final GameChangerService gameChangerService;
    private final UserSessionRepository userRepository;
    private final BirthdayGreetingService birthdayGreetingService;


    public TelegramClientBot(@Value("${bot.name}")String botUsername, @Value("${bot.token}")String botToken,
                             GameChangerService gameChangerService, UserSessionRepository userRepository, BirthdayGreetingService birthdayGreetingService) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.gameChangerService = gameChangerService;
        this.userRepository = userRepository;
        this.birthdayGreetingService = birthdayGreetingService;
    }

    public String getBotUsername() { return this.botUsername; }

    public String getBotToken() { return this.botToken; }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String messageText = update.getMessage().getText().trim();
        Long chatId = update.getMessage().getChatId();

       LocalDateTime today = LocalDateTime.now();
       boolean isEvenMinute = (today.getMinute() % 2 == 0);

       userRepository.findById(chatId).ifPresent(state -> {
           if (state.getBirthDate() != null && !today.equals(state.getLastBirthdayGreeting())) {
               String birthdayGreeting = birthdayGreetingService.getGreetingIfBirthday(state, isEvenMinute);

               if (birthdayGreeting != null && !birthdayGreeting.isBlank()) {
                   sendResponse(chatId, birthdayGreeting);

                   userRepository.saveAndFlush(state);
               }
           }
       });

        String response = gameChangerService.handleMessage(chatId, messageText);

        sendResponse(chatId, response);
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
