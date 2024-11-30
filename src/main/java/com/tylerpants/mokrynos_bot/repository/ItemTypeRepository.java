package com.tylerpants.mokrynos_bot.repository;

import com.tylerpants.mokrynos_bot.model.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTypeRepository extends JpaRepository<ItemType, Integer> {
}
