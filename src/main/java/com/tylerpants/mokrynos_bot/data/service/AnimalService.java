package com.tylerpants.mokrynos_bot.data.service;

import com.tylerpants.mokrynos_bot.data.model.Animal;
import com.tylerpants.mokrynos_bot.data.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;

    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    public List<Animal> findPageable(int p) {
//        p = (p == 0) ? 1 : p;
        return animalRepository.findAll(PageRequest.of(p, 3)).getContent();
    }

    public int count() {
        return (int) animalRepository.count();
    }

    public Animal findById(int id) {
        return animalRepository.findById(id).orElse(null);
    }
}
