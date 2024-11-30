package com.tylerpants.mokrynos_bot.service;

import com.tylerpants.mokrynos_bot.model.Animal;
import com.tylerpants.mokrynos_bot.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;

    public List<Animal> findAll() {
        return animalRepository.findAll();
    }
}
