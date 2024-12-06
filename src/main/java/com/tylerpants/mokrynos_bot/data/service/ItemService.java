package com.tylerpants.mokrynos_bot.data.service;

import com.tylerpants.mokrynos_bot.data.model.Item;
import com.tylerpants.mokrynos_bot.data.repository.ItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemsRepository itemsRepository;

    public List<Item> findNoFilters() {
        return itemsRepository.findAll();
    }

    public List<Item> findByFilters(String animalsArr, List<Integer> symptoms) {
        List<Item> list;

//        if(animalsArr.isBlank() && !symptoms.isEmpty()) {
//            list = itemsRepository.findAllBySymptom(symptoms);
//        } else if (!animalsArr.isBlank() && symptoms.isEmpty()) {
//
//            list = itemsRepository.findAllByAnimalsArr(animalsArr);
//        }
//        else {
//            list = itemsRepository.findAllByAnimalsArrAndSymptoms(animalsArr, symptoms);
//        }

        if(animalsArr.isBlank() && !symptoms.isEmpty()) {
            list = itemsRepository.findAllBySymptom(symptoms);
        }
        else {
            if(!symptoms.isEmpty()) {
                list = itemsRepository.findAllBySymptom(symptoms);
            } else {
                list = findNoFilters();
            }
            List<String> a = Arrays.stream(animalsArr.split(",")).toList();

            for (Item i : list) {
                if(!(new HashSet<>(Arrays.stream(i.getAnimalsArr().split(",")).toList()).containsAll(a))) {
                    list.remove(i);
                }
            }
        }
        return list;
    }
}
