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
    private String equipoLocalImagen;
    private String equipoVisitanteImagen;


    public JornadaDTO(Jornada jornada) {
        this.id = jornada.getId();
        this.fecha = jornada.getFecha();
        this.golLocal = jornada.getGolLocal();
        this.golVisitante = jornada.getGolVisitante();

        if (jornada.getEquipoLocal() != null) {
            this.equipoLocalNombre = jornada.getEquipoLocal().getNombre();
            this.equipoLocalImagen = jornada.getEquipoLocal().getImagen();
        }

        if (jornada.getEquipoVisitante() != null) {
            this.equipoVisitanteNombre = jornada.getEquipoVisitante().getNombre();
            this.equipoVisitanteImagen = jornada.getEquipoVisitante().getImagen();
        }
        this.arbitroNombre = jornada.getArbitro() != null ? jornada.getArbitro().getNombre() : null;
        this.estadioNombre = jornada.getEstadio() != null ? jornada.getEstadio().getNombre() : null;
    }


}
