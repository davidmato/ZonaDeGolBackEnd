package com.example.zonadegolbackend.dtos;

import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.enums.Posicion;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CrearJugador {
    private String nombre;
    private String apellido;
    private Posicion posicion;
    private Integer dorsal;
    private String imagen;
    private String dni;
    private LocalDateTime fechaNacimiento;
    private Usuario usuario;
    private String correo;

}