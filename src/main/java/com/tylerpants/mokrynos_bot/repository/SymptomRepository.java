package com.tylerpants.mokrynos_bot.repository;

import com.tylerpants.mokrynos_bot.model.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Integer> {
}
