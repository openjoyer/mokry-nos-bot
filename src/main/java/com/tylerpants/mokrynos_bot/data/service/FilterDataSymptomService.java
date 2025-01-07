package com.tylerpants.mokrynos_bot.data.service;

import com.tylerpants.mokrynos_bot.data.model.FilterDataSymptom;
import com.tylerpants.mokrynos_bot.data.model.Symptom;
import com.tylerpants.mokrynos_bot.data.repository.FilterDataSymptomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FilterDataSymptomService implements FilterDataService<FilterDataSymptom, Symptom> {
    private final FilterDataSymptomRepository filterRepository;

    @Override
    @Transactional
    public boolean add(Long chatId, Symptom symptom) {
        if(filterRepository.existsByChatIdAndSymptom(chatId, symptom)) {
            return false;
        }
//        if(symptom.getId() == 0) {
//            filterRepository.removeAllByChatId(chatId);
//        }
        else {
            filterRepository.removeFilterAllByChatId(chatId);
        }
        FilterDataSymptom filterSymptom = new FilterDataSymptom();
        filterSymptom.setSymptom(symptom);
        filterSymptom.setChatId(chatId);

        filterRepository.save(filterSymptom);
        return true;
    }

    @Override
    public List<FilterDataSymptom> findByChatId(Long chatId) {
        return filterRepository.findAllByChatId(chatId);
    }

    @Override
    @Transactional
    public void removeAllByChatId(Long chatId) {
        filterRepository.removeAllByChatId(chatId);
    }
}
