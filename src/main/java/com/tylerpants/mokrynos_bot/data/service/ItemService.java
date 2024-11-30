package com.tylerpants.mokrynos_bot.data.service;

import com.tylerpants.mokrynos_bot.data.repository.ItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemsRepository itemsRepository;
}
