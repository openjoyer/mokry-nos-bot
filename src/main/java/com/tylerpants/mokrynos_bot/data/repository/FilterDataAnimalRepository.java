package com.tylerpants.mokrynos_bot.data.repository;

import com.tylerpants.mokrynos_bot.data.model.Animal;
import com.tylerpants.mokrynos_bot.data.model.FilterDataAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FilterDataAnimalRepository extends JpaRepository<FilterDataAnimal, Integer> {

    List<FilterDataAnimal> findAllByChatId(Long chatId);

    boolean existsByChatIdAndAnimal(Long chatId, Animal animal);

    void removeAllByChatId(Long chatId);


    @Modifying
    @Transactional
    @Query("delete from FilterDataAnimal where chatId = :chatId and animal.id = 0")
    void removeFilterAllByChatId(@Param("chatId") Long chatId);
}
