package com.example.zonadegolbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipoInfoDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaFundacion;
    private String imagen;
    private String entrenadorNombre;
    private String entrenadorImagen;
    private String ligaNombre;
    private String ligaImagen;
    private Integer ligaId;
    private LocalDate entrenadorFechaNacimiento;
    private String jugadorId;
}
