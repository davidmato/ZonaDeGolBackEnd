package com.example.zonadegolbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadioDTO {
    private Integer id;
    private String nombre;
    private String direccion;
    private Integer aforo;
}
