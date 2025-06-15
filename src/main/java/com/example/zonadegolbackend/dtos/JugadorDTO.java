package com.example.zonadegolbackend.dtos;

import com.example.zonadegolbackend.enums.Posicion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JugadorDTO {
    private String nombre;
    private String apellido;
    private Posicion posicion;
    private Integer dorsal;
    private String imagen;
    private String dni;
    private LocalDate fechaNacimiento;
    private String correo;
    private Boolean activo;
    private Boolean expulsado;
    private String equipoNombre;
    private String ligaNombre;
    private String equipoFoto;
    private LocalDate fechaFundacion;
    private Integer equipoId;
}
