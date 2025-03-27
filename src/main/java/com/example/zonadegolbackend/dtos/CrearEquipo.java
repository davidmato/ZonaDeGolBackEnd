package com.example.zonadegolbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearEquipo {
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaFundacion;
    private String imagen;
    private String nombreEntrenador;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String dni;
    private String imagenEntrenador;
    private String username;
    private String password;
    private String correo;
    private Integer idLiga;
}