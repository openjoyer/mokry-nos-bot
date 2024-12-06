package com.tylerpants.mokrynos_bot.telegram;

import com.tylerpants.mokrynos_bot.data.model.Animal;
import com.tylerpants.mokrynos_bot.data.model.Symptom;

import java.util.List;

public class BotConstants {
    public static final String HELP_TEXT = """
            /start - Запустить бота
            /help - Информация о боте и его командах
            /filter - Отобразить применённые фильтры к поиску препаратов
            /animals - Выбор типа животного для поиска препаратов
            /symptoms - Выбор симптомов у животного
            /search - Поиск препаратов по выбранным категориям""";
    public static final String START_TEXT = """
            Привет! Добро пожаловать в бота Мокрого носа! Выбирай то, что нужно, ниже... 👇""";
    public static final String FILTER_TEXT = "⭐ Выбранные фильтры\n";
    public static final String FILTER_ANIMAL = "\n\uD83D\uDC49 Животные: ";
    public static final String FILTER_SYMPTOM = "\n\uD83D\uDC49 Симптомы: ";
    public static final String UNKNOWN_COMMAND = "Я не понимаю :( Пожалуйста, воспользуйтесь командами из списка /help";
    public static final String ANIMAL_INFO = "Выбор вида Животного";
    public static final String SYMPTOM_INFO = "Выбор Cимптомов заболевания";
    public static final String CHOSEN = "✅ Выбрано : ";
    public static final String START_BUTTON = "Старт";
    public static final String EXIT_BUTTON = "Вернуться в меню";
    public static final String UNDO_MESSAGE_TEXT = "Отменено";
    public static final String HELP_BUTTON = "\uD83D\uDCA1 Помощь";
    public static final String ANIMAL_BUTTON = "Выбор животного";
    public static final String SYMPTOM_BUTTON = "Выбор симптомов";
    public static final String FILTER_BUTTON = "\uD83D\uDD27 Фильтры";
    public static final String SEARCH_BUTTON = "\uD83D\uDD0D Поиск";
    public static final String CONFIRM_BUTTON = "Подтвердить";
    public static final String UNDO_BUTTON = "Отменить";
    public static final String CLEAR_FILTER = "Очистить фильтры";
    public static final String FILTERS_ARE_EMPTY = "Пусто";
    public static final String FILTERS_REMOVED = "✅ Фильтры очищены";
    public static final String ARROWS_ADVICE = "Листай позиции с помощью кнопок - стрелочек \uD83D\uDD3D";
    public static final String HELP_ADVICE = "⚡ Команды бота";
    public static final String FILTERS_APPLIED = "✅ Фильтры применены";
    public static final String ALREADY_CHOSEN = "❌ Уже выбрано : ";

    public static final String ITEMS_FOUND = "Найденные товары";

    public static String createItemMessage(String name, List<Animal> animals, Symptom symptom, String description, String catalogLink) {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append("\n");
        if (description != null && !description.isEmpty())
            builder.append("Описание :").append(description).append("\n");
        builder.append("Для кого: ").append(animals.stream().map(Animal::getName).toList().toString().replaceAll("[\\[\\]]", "")).append("\n");
        builder.append("При симптоме: ").append(symptom.getName()).append("\n");
        if (catalogLink != null && !catalogLink.isEmpty())
            builder.append("Ссылка на товар: ").append(catalogLink);

        return builder.toString();
    }
}
