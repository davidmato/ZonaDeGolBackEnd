package com.example.zonadegolbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadisticasDTO {
    private Integer partidosJugados;
    private Integer goles;
    private Integer asistencias;
    private Integer tarjetasAmarillas;
    private Integer tarjetasRojas;
    private Integer porteriaCero;
    private String nombreJugador;
    private String apellidoJugador;
    private String nombreLiga;
    private String nombreTemporada;
    private String nombreEquipo;
}
