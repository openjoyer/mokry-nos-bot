package com.tylerpants.mokrynos_bot.service;

import com.tylerpants.mokrynos_bot.model.Symptom;
import com.tylerpants.mokrynos_bot.repository.SymptomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
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
        return symptomRepository.findAll(PageRequest.of(p, 3)).getContent();
    }

    public int count() {
        return (int) symptomRepository.count();
    }

}
