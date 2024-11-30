package com.tylerpants.mokrynos_bot.telegram;

import com.tylerpants.mokrynos_bot.config.BotConfig;
import jakarta.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class MedsBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final BotButtons botButtons;

    @Autowired
    public MedsBot(BotConfig botConfig, BotButtons botButtons) {
        this.botConfig = botConfig;
        this.botButtons = botButtons;
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        Long chatId;
        String text;
        Integer messageId;

        if(update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            text = update.getCallbackQuery().getData();
            Message message = (Message) update.getCallbackQuery().getMessage();
            messageId = message.getMessageId();

            answer(chatId, text, messageId);
        } else if(update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            messageId = update.getMessage().getMessageId();

            if(update.getMessage().hasText()) {
                text = update.getMessage().getText();

                answer(chatId, text, messageId);

            }
        }
    }


    private void answer(Long chatId, String message, Integer prevMessageId) {
        if (message.equals("/start") || (message.contains("Старт")) || (message.contains("Вернуться в меню"))) {
            startAction(chatId);
        }
        else if (message.equals("/help") || message.contains("Помощь")) {
            helpAction(chatId);
        } else if (message.equals("/animals") || message.contains("Выбор животного")) {
            animalAction(chatId);
        } else if (message.equals("/symptoms") || message.contains("Выбор симптомов")) {
            symptomId(chatId, 0, -1);
        } else if (message.contains("to")) {
            int p = Integer.parseInt(message.split("\\s")[1]);
            symptomId(chatId, p, prevMessageId);
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            String response = "Я не понимаю :( Пожалуйста, воспользуйтесь командами из списка /help";
            sendMessage.setText(response);
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }

    }

    private void startAction(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(BotConstants.START_TEXT);
        sendMessage.setReplyMarkup(botButtons.initKeyboardMarkup2());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void helpAction(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(BotConstants.HELP_TEXT);
        sendMessage.setReplyMarkup(botButtons.exitMarkup());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void animalAction(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выберите животное:");
        sendMessage.setReplyMarkup(botButtons.animalTypeMarkup());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void symptomId(Long chatId, int p, Integer prevMessageId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выберите симптомы заболевания:");
        sendMessage.setReplyMarkup(botButtons.symptomTypeMarkup(p));

        if (prevMessageId != -1) {
            DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(chatId), prevMessageId);
            try {
                execute(deleteMessage);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }

        try {
            execute(sendMessage);
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


}
