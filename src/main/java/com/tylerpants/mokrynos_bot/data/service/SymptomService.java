package com.tylerpants.mokrynos_bot.data.service;

import com.tylerpants.mokrynos_bot.data.model.Symptom;
import com.tylerpants.mokrynos_bot.data.repository.SymptomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SymptomService {
    private final SymptomRepository symptomRepository;

    public List<Symptom> findAll() {
        return symptomRepository.findAll();
    }

    public List<Symptom> findPageable(int p) {
//        p = (p == 0) ? 1 : p;
        return symptomRepository.findAll(PageRequest.of(p, 3)).getContent();
    }

    public int count() {
        return (int) symptomRepository.count();
    }

    public Symptom findById(int id) {
        return symptomRepository.findById(id).orElse(null);
    }
}
