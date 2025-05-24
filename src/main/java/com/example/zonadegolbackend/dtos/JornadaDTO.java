package com.example.zonadegolbackend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JornadaDTO {

    private Integer id;
    private LocalDateTime fecha;
    private int golLocal;
    private int golVisitante;
    private String equipoLocalNombre;
    private String equipoVisitanteNombre;
    private String arbitroNombre;
    private String estadioNombre;

}
