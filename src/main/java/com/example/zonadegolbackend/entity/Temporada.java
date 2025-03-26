package com.example.zonadegolbackend.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "temporada", schema = "zona_de_gol", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Temporada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name="fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @ManyToOne
    @JoinColumn(name = "id_liga", referencedColumnName = "id")
    private Liga liga;
}
