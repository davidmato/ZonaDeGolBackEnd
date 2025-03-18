package com.example.zonadegolbackend.dtos;

import com.example.zonadegolbackend.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntrenadorDTO {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String dni;
    private String imagen;
    private String username;
    private String password;
    private String correo;
    private Rol rol;
}