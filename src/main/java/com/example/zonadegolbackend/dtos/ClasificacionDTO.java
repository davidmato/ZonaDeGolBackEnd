package com.example.zonadegolbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClasificacionDTO {
    private Integer puesto;
    private String nombre;
    private Integer partidosJugados;
    private Integer victorias;
    private Integer empates;
    private Integer derrotas;
    private Integer golAFavor;
    private Integer golEnContra;
    private Integer golDiferencia;
    private Integer puntos;
    private List<String> forma;
}
