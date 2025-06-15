package com.example.zonadegolbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "noticias", schema = "zona_de_gol", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Noticias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="imagen", nullable = true)
    private String imagen;

    @Column(name="titulo", nullable = false)
    private String titulo;

    @Column(name="descripcion", nullable = false)
    private String descripcion;

    @Column(name="fecha", nullable = false)
    private LocalDate fecha;


}
