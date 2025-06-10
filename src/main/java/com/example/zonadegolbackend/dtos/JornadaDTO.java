package com.example.zonadegolbackend.dtos;


import com.example.zonadegolbackend.entity.Jornada;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JornadaDTO {

    private Integer id;
    private LocalDateTime fecha;
    private int golLocal;
    private int golVisitante;
    private String equipoLocalNombre;
    private String equipoVisitanteNombre;
    private String arbitroNombre;
    private String estadioNombre;

    public JornadaDTO(Jornada jornada) {
        this.id = jornada.getId();
        this.fecha = jornada.getFecha();
        this.golLocal = jornada.getGolLocal();
        this.golVisitante = jornada.getGolVisitante();
        this.equipoLocalNombre = jornada.getEquipoLocal() != null ? jornada.getEquipoLocal().getNombre() : null;
        this.equipoVisitanteNombre = jornada.getEquipoVisitante() != null ? jornada.getEquipoVisitante().getNombre() : null;
        this.arbitroNombre = jornada.getArbitro() != null ? jornada.getArbitro().getNombre() : null;
        this.estadioNombre = jornada.getEstadio() != null ? jornada.getEstadio().getNombre() : null;
    }


}
