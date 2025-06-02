package com.example.zonadegolbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JornadaArbitroDTO {
    private Integer id;
    private int golLocal;
    private int golVisitante;
}
