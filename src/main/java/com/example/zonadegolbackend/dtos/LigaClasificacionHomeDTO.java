package com.example.zonadegolbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LigaClasificacionHomeDTO {

    private LigaHomeDTO liga;
    private List<ClasificacionHomeDTO> clasificacion;
}
