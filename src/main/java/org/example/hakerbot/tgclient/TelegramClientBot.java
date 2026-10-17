package org.example.hakerbot.tgclient;

import org.example.hakerbot.dto.TgBotResultDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramClientBot extends TelegramLongPollingBot {

    private final String botUsername;

    private final TgGameService tgGameService;

    public TelegramClientBot(TgGameService tgGameService,
                             @Value("${bot.name}") String botUsername,
                             @Value("${bot.token}")  String botToken) {
        super(botToken);
        this.tgGameService = tgGameService;
        this.botUsername = botUsername;

    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText();

            TgBotResultDto tgResultDto = tgGameService.processTgMessage(chatId, message);

            sendMessage(chatId, tgResultDto.toString());
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.err.println(e.getMessage());
        }
    }
}