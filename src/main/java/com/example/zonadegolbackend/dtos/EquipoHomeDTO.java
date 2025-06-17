package com.example.zonadegolbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipoHomeDTO {

    private Integer id;
    private String nombre;
    private String imagen;
}
