package com.tylerpants.mokrynos_bot.data.repository;

import com.tylerpants.mokrynos_bot.data.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {
}
