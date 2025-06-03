package com.example.zonadegolbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CorreoAdminDTO {

    private String asunto;
    private String contenido;
}
