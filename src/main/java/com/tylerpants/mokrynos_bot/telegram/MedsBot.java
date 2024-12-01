package com.tylerpants.mokrynos_bot.telegram;

import com.tylerpants.mokrynos_bot.config.BotConfig;
import com.tylerpants.mokrynos_bot.data.model.Animal;
import com.tylerpants.mokrynos_bot.data.model.Symptom;
import com.tylerpants.mokrynos_bot.data.service.*;
import jakarta.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class MedsBot extends TelegramLongPollingBot {

    private final FilterDataSymptomService filterDataSymptomService;

    private final FilterDataAnimalService filterDataAnimalService;

    private final BotConfig botConfig;
    private final BotButtons botButtons;

    private final SymptomService symptomService;

    private final AnimalService animalService;

    private final ItemService itemService;

    @Autowired
    public MedsBot(FilterDataSymptomService filterDataSymptomService,
                   FilterDataAnimalService filterDataAnimalService,
                   BotConfig botConfig, BotButtons botButtons,
                   SymptomService symptomService,
                   AnimalService animalService,
                   ItemService itemService) {
        this.filterDataSymptomService = filterDataSymptomService;
        this.filterDataAnimalService = filterDataAnimalService;
        this.botConfig = botConfig;
        this.botButtons = botButtons;
        this.symptomService = symptomService;
        this.animalService = animalService;
        this.itemService = itemService;

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
        if (message.equals("/start") || (message.contains(BotConstants.START_BUTTON)) || (message.contains(BotConstants.EXIT_BUTTON))) {
            startAction(chatId);
        }
        else if (message.equals("/filter") || message.contains(BotConstants.FILTER_BUTTON)) {
            System.out.println("ДОДЕЛАТЬ!!!");
        }
        else if (message.equals("/search") || message.contains(BotConstants.SEARCH_BUTTON)) {
            System.out.println("ДОДЛЕАТЬ!!!!");
        }
        else if (message.equals("/help") || message.contains(BotConstants.HELP_BUTTON) ||
                message.equals("/animals") || message.contains(BotConstants.ANIMAL_BUTTON) ||
                message.equals("/symptoms") || message.contains(BotConstants.SYMPTOM_BUTTON)) {
            transferAction(chatId, message);
        }

        else if (message.contains("animal")) {
            int animalId = Integer.parseInt(message.split("\\s")[1]);
            animalChoiceAction(chatId, animalId, prevMessageId);
        }
        else if(message.contains("ago")) {
            int p = Integer.parseInt(message.split("\\s")[1]);
            animalAction(chatId, p, prevMessageId);
        }

        else if(message.contains("symptom")) {
            int symptomId = Integer.parseInt(message.split("\\s")[1]);
            symptomChoiceAction(chatId, symptomId, -1);
        }
        else if (message.contains(BotConstants.CONFIRM_BUTTON)) {
            symptomChoiceAction(chatId, -1, prevMessageId);
        }
        else if (message.contains("sgo")) {
            int p = Integer.parseInt(message.split("\\s")[1]);
            symptomAction(chatId, p, prevMessageId);
        }
        else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(BotConstants.UNKNOWN_COMMAND);
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
        sendMessage.setReplyMarkup(botButtons.initKeyboardMarkup());

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
        sendMessage.setReplyMarkup(botButtons.exitMarkup(false));

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void animalAction(Long chatId, int p, Integer prevMessageId) {
        if(prevMessageId == -1) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(BotConstants.ANIMAL_INFO);
            sendMessage.setReplyMarkup(botButtons.animalTypeMarkup(p));

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        } else {
            EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
            editMessage.setChatId(String.valueOf(chatId));
            editMessage.setMessageId(prevMessageId);
            editMessage.setReplyMarkup(botButtons.animalTypeMarkup(p));
            try {
                execute(editMessage);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }


    }

    private void transferAction(Long chatId, String messageText) {
        boolean isAnimal = (messageText.equals("/animals") || messageText.contains(BotConstants.ANIMAL_BUTTON));
        boolean isSymptom = messageText.equals("/symptoms") || messageText.contains(BotConstants.SYMPTOM_BUTTON);
        boolean isHelp = messageText.equals("/help") || messageText.contains(BotConstants.HELP_BUTTON);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if(isAnimal || isSymptom) {
            sendMessage.setText(BotConstants.ARROWS_ADVICE);
        }
        else if (isHelp) {
            sendMessage.setText(BotConstants.HELP_ADVICE);
        }

        sendMessage.setReplyMarkup(botButtons.exitMarkup(isSymptom));

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        if (isHelp) {
            helpAction(chatId);
        }
        else if(isAnimal) {
            animalAction(chatId, 0, -1);
        }
        else if (isSymptom) {
            symptomAction(chatId, 0, -1);
        }
    }

    private void symptomAction(Long chatId, int p, int prevMessageId) {
        if (prevMessageId == -1) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(BotConstants.SYMPTOM_INFO);
            sendMessage.setReplyMarkup(botButtons.symptomTypeMarkup(p));

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        } else {
            EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
            editMessage.setChatId(String.valueOf(chatId));
            editMessage.setMessageId(prevMessageId);
            editMessage.setReplyMarkup(botButtons.symptomTypeMarkup(p));
            try {
                execute(editMessage);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }
    }

    private void symptomChoiceAction(Long chatId, int symptomId, int prevMessageId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if(prevMessageId == -1) {
            Symptom symptom = symptomService.findById(symptomId);

            if(!filterDataSymptomService.add(chatId, symptom)) {
                sendMessage.setText(BotConstants.ALREADY_CHOSEN + symptom.getName());
            }
            else {
                sendMessage.setText(BotConstants.CHOSEN + symptom.getName());
            }
        }
        else {
            sendMessage.setText(BotConstants.FILTERS_APPLIED);
            sendMessage.setReplyMarkup(botButtons.initKeyboardMarkup());
            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(chatId);
            deleteMessage.setMessageId(prevMessageId);

            try {
                execute(deleteMessage);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void animalChoiceAction(Long chatId, int animalId, int prevMessageId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(botButtons.initKeyboardMarkup());

        Animal animal = animalService.findById(animalId);
        if(!filterDataAnimalService.add(chatId, animal)) {
            sendMessage.setText(BotConstants.ALREADY_CHOSEN + animal.getName());
        }
        else {
            sendMessage.setText(BotConstants.FILTERS_APPLIED);
        }
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(prevMessageId);

        try {
            execute(sendMessage);
            execute(deleteMessage);
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
