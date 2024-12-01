package com.tylerpants.mokrynos_bot.data.service;

import com.tylerpants.mokrynos_bot.data.model.Item;
import com.tylerpants.mokrynos_bot.data.repository.ItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemsRepository itemsRepository;

    @Transactional
    public List<Item> findAll() {
        return itemsRepository.findAll();
    }

    public List<Item> findByFilters() {
        return null;
    }
}
