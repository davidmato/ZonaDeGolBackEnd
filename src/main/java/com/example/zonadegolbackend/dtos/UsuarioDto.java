package com.example.zonadegolbackend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private String username;
    private String correo;
    private String password;
    private String nombreEntrenador;
    private String apellido;
    private LocalDateTime fechaNacimiento;
    private String dni;
    private String imagenEntrenador;
}
