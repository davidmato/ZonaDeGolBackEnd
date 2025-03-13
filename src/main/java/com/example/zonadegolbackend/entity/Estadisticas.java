package com.example.zonadegolbackend.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "estadisticas", schema = "zona_de_gol", catalog = "postgres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Estadisticas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "partidos_jugados", nullable = false)
    private Integer partidosJugados;

    @Column(name="goles", nullable = false)
    private Integer goles;

    @Column(name="asistencias", nullable = false)
    private Integer asistencias;

    @Column(name="tarjetas_amarillas", nullable = false)
    private Integer tarjetasAmarillas;

    @Column(name="tarjetas_rojas", nullable = false)
    private Integer tarjetasRojas;


    @Column(name="porteria_cero", nullable = false)
    private Integer porteriaCero;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugador jugador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_temporada", nullable = false)
    private Temporada temporada;



}
