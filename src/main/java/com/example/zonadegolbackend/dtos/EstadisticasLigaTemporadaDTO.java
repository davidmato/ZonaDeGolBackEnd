package com.example.zonadegolbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticasLigaTemporadaDTO {
    private Integer partidosJugados;
    private Integer goles;
    private Integer asistencias;
    private Integer tarjetasAmarillas;
    private Integer tarjetasRojas;
    private Integer porteriaCero;
    private String nombre;
    private String apellido;
}
