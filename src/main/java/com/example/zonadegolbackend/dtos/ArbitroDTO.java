package com.example.zonadegolbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArbitroDTO {
    private Integer id;
    private String username;
    private String correo;
    private String password;
    private String nombreArbitro;
    private String apellido;
    private String numColegiado;
    private String dni;

}
