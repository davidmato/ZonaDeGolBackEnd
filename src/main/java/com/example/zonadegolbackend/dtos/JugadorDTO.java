package com.example.zonadegolbackend.dtos;

import com.example.zonadegolbackend.enums.Posicion;
import lombok.Data;

import java.time.LocalDate;

@Data
public class JugadorDTO {
    private String nombre;
    private String apellido;
    private Posicion posicion;
    private Integer dorsal;
    private String imagen;
    private String dni;
    private LocalDate fechaNacimiento;
    private String correo;
    private String equipo;
}
