package com.tylerpants.mokrynos_bot.data.repository;

import com.tylerpants.mokrynos_bot.data.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {

    @Query("from Item where animalsArr = :animalsArr and symptom.id in :symptom")
    List<Item> findAllByAnimalsArrAndSymptoms(String animalsArr, List<Integer> symptom);

    @Query("from Item where animalsArr = :animalsArr")
    List<Item> findAllByAnimalsArr(String animalsArr);

    @Query("from Item where symptom.id in :symptom")
    List<Item> findAllBySymptom(List<Integer> symptom);
}
