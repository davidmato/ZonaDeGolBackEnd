package com.example.zonadegolbackend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "jornada", schema = "zona_de_gol", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Jornada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name="gol_local",nullable = false)
    private Integer golLocal;

    @Column(name="gol_visitante",nullable = false)
    private Integer golVisitante;

    @ManyToOne
    @JoinColumn(name = "id_equipo_local", nullable = false)
    private Equipo equipoLocal;

    @ManyToOne
    @JoinColumn(name = "id_equipo_visitante", nullable = false)
    private Equipo equipoVisitante;

    @ManyToOne
    @JoinColumn(name = "id_temporada", nullable = false)
    private Temporada temporada;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_arbitro", nullable = true)
    private Arbitro arbitro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estadio")
    private Estadio estadio;

}
