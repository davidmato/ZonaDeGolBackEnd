package com.example.zonadegolbackend.dtos;

import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.enums.Posicion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrearJugador {
    private Integer id;
    private String nombre;
    private String apellido;
    private Posicion posicion;
    private Integer dorsal;
    private String imagen;
    private String dni;
    private LocalDate fechaNacimiento;
    private String correo;

}