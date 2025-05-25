package com.example.zonadegolbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "estadio", schema = "zona_de_gol", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Estadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "aforo", nullable = true)
    private Integer aforo;

    @OneToMany(mappedBy = "estadio", fetch = FetchType.LAZY)
    private List<Jornada> jornadas;

}
