package com.example.zonadegolbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClasificacionHomeDTO {

    private Integer puesto;
    private Integer puntos;
    private Integer partidosJugados;
    private Integer victorias;
    private Integer empates;
    private Integer derrotas;
    private Integer golAFavor;
    private Integer golEnContra;
    private Integer golDiferencia;
    private EquipoHomeDTO equipo;
}
