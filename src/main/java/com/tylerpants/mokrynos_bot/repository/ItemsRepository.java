package com.tylerpants.mokrynos_bot.repository;

import com.tylerpants.mokrynos_bot.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {
}
