package com.example.zonadegolbackend.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trofeo", schema = "zona_de_gol", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Trofeo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "imagen", nullable = false)
    private String imagen;

}
