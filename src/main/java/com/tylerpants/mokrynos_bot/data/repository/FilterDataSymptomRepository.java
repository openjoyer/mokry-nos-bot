package com.tylerpants.mokrynos_bot.data.repository;

import com.tylerpants.mokrynos_bot.data.model.FilterDataSymptom;
import com.tylerpants.mokrynos_bot.data.model.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FilterDataSymptomRepository extends JpaRepository<FilterDataSymptom, Integer> {
    List<FilterDataSymptom> findAllByChatId(Long chatId);

    boolean existsByChatIdAndSymptom(Long chatId, Symptom symptom);

    void removeAllByChatId(Long chatId);

    @Modifying
    @Transactional
    @Query("delete from FilterDataSymptom where chatId = :chatId and symptom.id = 0")
    void removeFilterAllByChatId(@Param("chatId") Long chatId);
}
