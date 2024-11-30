package com.tylerpants.mokrynos_bot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_type")

@RequiredArgsConstructor
@Getter
@Setter
public class ItemType {
    @Id
    private Integer id;

    @Column(name = "name")
    private String name;
}
