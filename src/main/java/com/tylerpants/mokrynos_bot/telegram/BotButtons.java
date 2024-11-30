package com.tylerpants.mokrynos_bot.telegram;

import com.tylerpants.mokrynos_bot.data.model.Animal;
import com.tylerpants.mokrynos_bot.data.model.Symptom;
import com.tylerpants.mokrynos_bot.data.service.AnimalService;
import com.tylerpants.mokrynos_bot.data.service.SymptomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class BotButtons {
    private final AnimalService animalService;
    private final SymptomService symptomService;

    @Autowired
    public BotButtons(AnimalService animalService, SymptomService symptomService) {
        this.animalService = animalService;
        this.symptomService = symptomService;
    }

    public ReplyKeyboardMarkup initKeyboardMarkup2() {
        KeyboardButton startButton = new KeyboardButton(BotConstants.START_BUTTON);
        KeyboardButton helpButton = new KeyboardButton(BotConstants.HELP_BUTTON);
        KeyboardButton animalButton = new KeyboardButton(BotConstants.ANIMAL_BUTTON);
        KeyboardButton symptomButton = new KeyboardButton(BotConstants.SYMPTOM_BUTTON);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(startButton);
        row1.add(helpButton);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(animalButton);
        row2.add(symptomButton);

        List<KeyboardRow> rows = List.of(row1, row2);

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(rows);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);

        return markup;
    }

    public ReplyKeyboardMarkup animalTypeMarkup() {
        List<Animal> list = animalService.findAll();

        KeyboardRow rowInline = new KeyboardRow();
        List<KeyboardRow> rowsInLine = new ArrayList<>();

        for (Animal a : list) {
            KeyboardButton button = new KeyboardButton(a.getName());
            rowInline.add(button);

            if(rowInline.size() == 3) {
                rowsInLine.add(rowInline);
                rowInline = new KeyboardRow();
            }
        }
        if(!rowInline.isEmpty()) {
            rowsInLine.add(rowInline);
        }
        rowsInLine.add(exitRow());

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(rowsInLine);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);

        return markup;
    }

    public InlineKeyboardMarkup animalTypeMarkup(int p) {
        List<Animal> list = animalService.findPageable(p);
        int pagesCount = (int) (double) (animalService.count() / 3);

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

        for(Animal a : list) {
            InlineKeyboardButton button = new InlineKeyboardButton(a.getName());
            button.setCallbackData("/animal "+a.getId());
            rowInline.add(button);

            if(rowInline.size() == 3) {
                rowsInLine.add(rowInline);
                rowInline = new ArrayList<>();
            }
        }
        if(!rowInline.isEmpty()) {
            rowsInLine.add(rowInline);
        }

        List<InlineKeyboardButton> pagesRow = new ArrayList<>();

        if(p != 0) {
            InlineKeyboardButton prevButton = new InlineKeyboardButton("←");
            prevButton.setCallbackData("/ago " + (p - 1));
            pagesRow.add(prevButton);
        }

        if(p < pagesCount) {
            InlineKeyboardButton nextButton = new InlineKeyboardButton("→");
            nextButton.setCallbackData("/ago " + (p + 1));
            pagesRow.add(nextButton);
        }

        rowsInLine.add(pagesRow);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }

    public InlineKeyboardMarkup symptomTypeMarkup(int p) {
        List<Symptom> list = symptomService.findPageable(p);
        int pagesCount = (int) (double) (symptomService.count() / 3);

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();

        for (Symptom a : list) {
            InlineKeyboardButton button = new InlineKeyboardButton(a.getName());
            button.setCallbackData("/symptom "+a.getId());
            rowInline.add(button);

            if(rowInline.size() == 3) {
                rowsInLine.add(rowInline);
                rowInline = new ArrayList<>();
            }
        }
        if(!rowInline.isEmpty()) {
            rowsInLine.add(rowInline);
        }

        List<InlineKeyboardButton> pagesRow = new ArrayList<>();

        if(p != 0) {
            InlineKeyboardButton prevButton = new InlineKeyboardButton("←");
            prevButton.setCallbackData("/sgo " + (p - 1));
            pagesRow.add(prevButton);
        }

        if(p < pagesCount) {
            InlineKeyboardButton nextButton = new InlineKeyboardButton("→");
            nextButton.setCallbackData("/sgo " + (p + 1));
            pagesRow.add(nextButton);
        }

        rowsInLine.add(pagesRow);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }

    public ReplyKeyboardMarkup exitMarkup() {
        List<KeyboardRow> rowsInLine = new ArrayList<>();
        rowsInLine.add(exitRow());


        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        markup.setKeyboard(rowsInLine);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);

        return markup;
    }

    private KeyboardRow exitRow() {
        KeyboardButton exitButton = new KeyboardButton(BotConstants.EXIT_BUTTON);

        KeyboardRow exitRow = new KeyboardRow();
        exitRow.add(exitButton);

        return exitRow;
    }
}
