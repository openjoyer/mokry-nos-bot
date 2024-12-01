package com.tylerpants.mokrynos_bot.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filter_data_animals")

@Getter
@RequiredArgsConstructor
@Setter
public class FilterDataAnimal {
    @Id
    @Column(name = "id_data")
    private Integer id;

    @Column(name = "chat_id")
    private Integer chatId;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;
}
