package com.tylerpants.mokrynos_bot;

import com.tylerpants.mokrynos_bot.config.BotConfig;
import jakarta.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class MedsBot extends TelegramLongPollingBot implements BotCommands {

    private final BotConfig botConfig;
    private final BotButtons botButtons;

    @Autowired
    public MedsBot(BotConfig botConfig, BotButtons botButtons) {
        this.botConfig = botConfig;
        this.botButtons = botButtons;

        try {
            this.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        Long chatId;
        String userName;
        String message;
//        Integer messageId;

        if(update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userName = update.getCallbackQuery().getFrom().getFirstName();
            message = update.getCallbackQuery().getData();
//            messageId = Integer.parseInt(update.getCallbackQuery().getInlineMessageId());

            answer(chatId, message, userName);
        } else if(update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userName = update.getMessage().getFrom().getFirstName();
//            messageId = update.getMessage().getMessageId();

            if(update.getMessage().hasText()) {
                message = update.getMessage().getText();

                answer(chatId, message, userName);

            }
        }
    }


    private void answer(Long chatId, String message, String userName) {
        if (message.equals("/start") || (message.contains("Старт")) || (message.contains("Вернуться в меню"))) {
            startAction(chatId, userName);
        }
        else if (message.equals("/help") || message.contains("Помощь")) {
            helpAction(chatId);
        } else if (message.equals("/animals") || message.contains("Выбор животного")) {
            animalAction(chatId);
        } else if (message.equals("/symptoms") || message.contains("Выбор симптомов")) {
            symptomId(chatId, 0);
        } else if (message.contains("to")) {
            int p = Integer.parseInt(message.split("\\s")[1]);
            symptomId(chatId, p);
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

    private void startAction(Long chatId, String userName) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Привет, " + userName + "! Добро пожаловать в бота Мокрого носа! Пиши то что нужно ниже...");
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
        sendMessage.setText(HELP_TEXT);
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

    private void symptomId(Long chatId, int p) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выберите симптомы заболевания:");
        sendMessage.setReplyMarkup(botButtons.symptomTypeMarkup(p));

//        if(p > 0) {
//            DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(chatId), prevMessageId);
//            try {
//                execute(deleteMessage);
//            } catch (TelegramApiException e) {
//                log.error(e.getMessage());
//            }
//        }

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
