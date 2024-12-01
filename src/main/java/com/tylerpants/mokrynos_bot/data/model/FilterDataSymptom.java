package com.tylerpants.mokrynos_bot.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filter_data_symptoms")

@Getter
@RequiredArgsConstructor
@Setter
public class FilterDataSymptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_data")
    private Integer id;

    @Column(name = "chat_id")
    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "symptom_id")
    private Symptom symptom;

    @Override
    public String toString() {
        return symptom.toString();
    }
}
