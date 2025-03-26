package com.example.zonadegolbackend.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "clasificacion", schema = "zona_de_gol", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Clasificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "puesto", nullable = false)
    private Integer puesto;

    @Column(name="victorias", nullable = false)
    private Integer victorias;

    @Column(name="empates", nullable = false)
    private Integer empates;

    @Column(name="derrotas", nullable = false)
    private Integer derrotas;

    @Column(name="goles_a_favor", nullable = false)
    private Integer golAFavor;


    @Column(name="goles_en_contra", nullable = false)
    private Integer golEnContra;


    @Column(name="goles_diferencia", nullable = false)
    private Integer golDiferencia;


    @Column(name="puntos", nullable = false)
    private Integer puntos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipo", nullable = false)
    private Equipo equipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_temporada", nullable = false)
    private Temporada temporada;


}
