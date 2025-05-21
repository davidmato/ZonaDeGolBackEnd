package com.example.zonadegolbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArbitroDTO {
    private String username;
    private String correo;
    private String password;
    private String nombreArbitro;
    private String apellido;

}
