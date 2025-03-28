package com.example.zonadegolbackend.dtos;

import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Temporada;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrearClasificacionDTO {

    private Equipo equipo;
    private Temporada temporada;

}
