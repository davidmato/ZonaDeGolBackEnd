package com.example.zonadegolbackend.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "temporada_liga", schema = "zona_de_gol", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class TemporadaLiga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "id_temporada", referencedColumnName = "id")
    private Temporada temporada;

    @ManyToOne
    @JoinColumn(name = "id_liga", referencedColumnName = "id")
    private Liga liga;
}
