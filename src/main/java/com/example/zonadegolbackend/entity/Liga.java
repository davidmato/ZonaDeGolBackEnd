package com.example.zonadegolbackend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "liga", schema = "zona_de_gol", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Liga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name="num_equipos", nullable = false)
    private Integer numEquipos;

    @Column(name="descripcion", nullable = false)
    private String descripcion;

    @Column(name="fecha_fundacion", nullable = false)
    private LocalDate fecha_fundacion;

    @Column(name="imagen", nullable = false)
    private String imagen;
}
