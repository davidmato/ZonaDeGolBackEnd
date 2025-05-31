package com.example.zonadegolbackend.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadisticaTopDTO {

    private String nombreCompleto;
    private String equipo;
    private String posicion;
    private String imagen;
    private int valor;  //IMPORTANTEEEEE-> goles, asistencias o porterías a cero
}
