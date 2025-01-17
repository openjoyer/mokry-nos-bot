package com.tylerpants.mokrynos_bot.data.service;

import com.tylerpants.mokrynos_bot.data.model.Animal;
import com.tylerpants.mokrynos_bot.data.model.FilterDataAnimal;
import com.tylerpants.mokrynos_bot.data.repository.FilterDataAnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FilterDataAnimalService implements FilterDataService<FilterDataAnimal, Animal> {
    private final FilterDataAnimalRepository filterRepository;

    @Override
    @Transactional
    public boolean add(Long chatId, Animal animal) {
        if(filterRepository.existsByChatIdAndAnimal(chatId, animal)) {
            return false;
        }
//        if(animal.getId() == 0) {
//            filterRepository.removeAllByChatId(chatId);
//        }
        else {
            filterRepository.removeFilterAllByChatId(chatId);
        }
        FilterDataAnimal filterAnimal = new FilterDataAnimal();
        filterAnimal.setAnimal(animal);
        filterAnimal.setChatId(chatId);

        filterRepository.save(filterAnimal);
        return true;
    }

    @Override
    public List<FilterDataAnimal> findByChatId(Long chatId) {
        return filterRepository.findAllByChatId(chatId);
    }

    @Override
    @Transactional
    public void removeAllByChatId(Long chatId) {
        filterRepository.removeAllByChatId(chatId);
    }
}
