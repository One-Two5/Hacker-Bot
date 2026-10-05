package org.example.hakerbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class HackerBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;
    private final Map<Long, String> userStates = new ConcurrentHashMap<>();

    public HackerBot(String botUsername, String botToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() { return this.botUsername; }

    @Override
    public String getBotToken() { return this.botToken; }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String messageText = update.getMessage().getText().trim().toLowerCase();
        Long chatId = update.getMessage().getChatId();
        String currentState = userStates.getOrDefault(chatId, "START");

        if (messageText.equals("/start")) {
            sendText(chatId, "Соединение установлено. Ты подключился к защищенному шлюзу. Напиши команду <сканировать порты>.");
            userStates.put(chatId, "AWAITING_SCAN");
            return;
        }

        switch (currentState) {
            case "AWAITING_SCAN":
                if (messageText.equals("сканировать порты")) {
                    sendText(chatId, "🔍 Найдена уязвимость: порт 8080. Напиши: <подключить порт 8080>");
                    userStates.put(chatId, "AWAITING_CONNECT");
                } else {
                    sendText(chatId, "❌ Введи команду: <сканировать порты>");
                }
                break;
            case "AWAITING_CONNECT":
                if (messageText.equals("подключить порт 8080")) {
                    sendText(chatId, "🔓 Доступ получен. Выбери действие: <скачать архив* или *майнить крипту>");
                    userStates.put(chatId, "CHOOSE_TARGET");
                } else {
                    sendText(chatId, "❌ Введи команду: <подключить порт 8080>");
                }
                break;
            case "CHOOSE_TARGET":
                if (messageText.equals("майнить крипту")) {
                    sendText(chatId, "⚠️ Сервер перегрелся, миссия провалена! Напиши `/start` для сброса.");
                    userStates.put(chatId, currentState);
                } else if (messageText.equals("скачать архив")) {
                    sendText(chatId, "⏳ Скачивание 90%... Тревога! Введи ответ уравнения: <(5 + 5) * 5>");
                    userStates.put(chatId, "AWAITING_MATH_ANSWER");
                } else {
                    sendText(chatId, "❌ Выбери: *скачать архив* или *майнить крипту*");
                }
                break;
            case "AWAITING_MATH_ANSWER":
                if (messageText.equals("50")) {
                    sendText(chatId, "🎉 Победа! Архив скачан. Напиши `/start` для новой игры.");
                } else {
                    sendText(chatId, "❌ Ошибка вычислений! Данные удалены. Напиши `/start` для перезапуска.");
                }
                userStates.put(chatId, "START");
                break;
            default:
                sendText(chatId, "Напиши `/start`, чтобы начать игру.");
                break;
        }
    }

    private void sendText(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
