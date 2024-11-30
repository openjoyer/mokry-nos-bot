package com.tylerpants.mokrynos_bot.config;

import com.tylerpants.mokrynos_bot.telegram.MedsBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class BotInitializer {
    private final MedsBot medsBot;

    @Autowired
    public BotInitializer(MedsBot medsBot) {
        this.medsBot = medsBot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(medsBot);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
