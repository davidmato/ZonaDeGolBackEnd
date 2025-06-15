package com.example.zonadegolbackend.dtos;

import com.example.zonadegolbackend.entity.Jornada;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JornadaConIdsDTO {

    private Integer id;
    private LocalDateTime fecha;
    private int golLocal;
    private int golVisitante;

    private Integer equipoLocalId;
    private String equipoLocalNombre;

    private Integer equipoVisitanteId;
    private String equipoVisitanteNombre;

    private String equipoLocalImagen;
    private String equipoVisitanteImagen;

    private String arbitroNombre;
    private String estadioNombre;

    public JornadaConIdsDTO(Jornada jornada) {
        this.id = jornada.getId();
        this.fecha = jornada.getFecha();
        this.golLocal = jornada.getGolLocal();
        this.golVisitante = jornada.getGolVisitante();

        this.equipoLocalId = jornada.getEquipoLocal() != null ? jornada.getEquipoLocal().getId() : null;
        this.equipoLocalNombre = jornada.getEquipoLocal() != null ? jornada.getEquipoLocal().getNombre() : null;
        this.equipoLocalImagen = jornada.getEquipoLocal().getImagen();

        this.equipoVisitanteId = jornada.getEquipoVisitante() != null ? jornada.getEquipoVisitante().getId() : null;
        this.equipoVisitanteNombre = jornada.getEquipoVisitante() != null ? jornada.getEquipoVisitante().getNombre() : null;
        this.equipoVisitanteImagen = jornada.getEquipoVisitante().getImagen();

        this.arbitroNombre = jornada.getArbitro() != null ? jornada.getArbitro().getNombre() : null;
        this.estadioNombre = jornada.getEstadio() != null ? jornada.getEstadio().getNombre() : null;
    }
}
