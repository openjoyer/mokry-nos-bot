package com.tylerpants.mokrynos_bot.data.service;

import com.tylerpants.mokrynos_bot.data.model.Animal;

import java.util.List;

public interface FilterDataService<T, K> {

    boolean add(Long chatId, K k);
    List<T> findByChatId(Long chatId);
    void removeAllByChatId(Long chatId);
}
