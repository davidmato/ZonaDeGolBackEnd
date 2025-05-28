package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.dtos.EstadioDTO;
import com.example.zonadegolbackend.dtos.GenerarJornadaDTO;
import com.example.zonadegolbackend.dtos.JornadaDTO;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Estadio;
import com.example.zonadegolbackend.entity.Jornada;
import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.repository.JornadaRepository;
import com.example.zonadegolbackend.services.JornadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jornada")
public class JornadaController {

    private final JornadaService jornadaService;
    private final JornadaRepository jornadaRepository;

    @GetMapping("/listar")
    public List<JornadaDTO> findAll() {
        return jornadaService.findAll();
    }

    @GetMapping("/estadios")
    public List<EstadioDTO> findAllEstadios() {return jornadaService.findAllEstadios();}

    @PostMapping("/crear")
    public Jornada crearJornada(@RequestBody JornadaDTO jornada) {
        return jornadaService.crearJornada(jornada);
    }

    @PutMapping("/editar/{id}")
    public Jornada editarJornada(@PathVariable Integer id, @RequestBody JornadaDTO jornada) {
        return jornadaService.editarJornada(id, jornada);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarJornada(@PathVariable Integer id) {
        jornadaService.eliminarJornada(id);
    }

    @PostMapping("/generar")
    public List<JornadaDTO> generarJornadas(@RequestBody GenerarJornadaDTO request) {
        return jornadaService.generarJornadas(request.getEquipos());
    }

    @PutMapping("/actualizar-puntos")
    public void actualizarPuntos(@RequestBody Jornada jornada) {
        jornadaService.actualizarPuntos(jornada);
    }

    @GetMapping("/equipo/{equipoId}")
    public List<Jornada> obtenerJornadasPorEquipo(@PathVariable Integer equipoId) {
        return jornadaRepository.findByEquipoLocal_IdOrEquipoVisitante_Id(equipoId, equipoId);
    }


}
