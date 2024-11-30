package com.tylerpants.mokrynos_bot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "items")
@RequiredArgsConstructor
@Getter
@Setter
public class Item {
    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "animal_type")
    private Animal animalType;

    @ManyToOne
    @JoinColumn(name = "item_type")
    private ItemType itemType;

    @ManyToOne
    @JoinColumn(name = "symptoms")
    private Symptom symptom;

    @Column(name = "description")
    private String description;
}
