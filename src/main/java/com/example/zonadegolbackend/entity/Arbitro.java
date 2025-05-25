package com.example.zonadegolbackend.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import com.example.zonadegolbackend.entity.Jornada;

@Entity
@Table(name = "arbitro", schema = "zona_de_gol", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Arbitro {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "num_colegiado", nullable = false)
    private String numColegiado;

    @Column(name = "dni", nullable = false)
    private String dni;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "arbitro")
    private List<Jornada> jornadasDirigidas;
}
