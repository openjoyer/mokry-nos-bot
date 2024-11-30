package com.tylerpants.mokrynos_bot.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "animal_type")

@RequiredArgsConstructor
@Getter
@Setter
public class Animal {
    @Id
    private Integer id;

    @Column(name = "name")
    private String name;
}