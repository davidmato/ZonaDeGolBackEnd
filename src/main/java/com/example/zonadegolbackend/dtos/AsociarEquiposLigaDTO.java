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
public class AsociarEquiposLigaDTO {

    private Integer idLiga;
    private List<Integer> idEquipos;
    private Integer idTemporada;

}
