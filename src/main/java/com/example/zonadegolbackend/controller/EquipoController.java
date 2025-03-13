package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.dtos.AsociarEquiposLigaDTO;
import com.example.zonadegolbackend.dtos.CrearEquipo;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.services.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/equipo")
public class EquipoController {

    private final EquipoService equipoService;

    @GetMapping("/listar")
    public List<Equipo> listarEquipos() {
        return equipoService.findAll();
    }

    @PostMapping("/crear")
    public Equipo crearEquipo(@RequestBody CrearEquipo equipo) {
        return equipoService.create(equipo);
    }

    @PutMapping("/editar/{idEquipo}")
    public Equipo editarEquipo(@PathVariable Integer idEquipo, @RequestBody CrearEquipo equipo) {
        return equipoService.update(idEquipo, equipo);
    }

    @PostMapping("/asociarEquiposConLiga")
    public void asociarEquiposConLiga(@RequestBody AsociarEquiposLigaDTO request) {
        equipoService.associateTeamsWithLeague(request.getIdLiga(), request.getIdEquipos(), request.getIdTemporada());
    }

    @PostMapping("/asociarTemporadaConEquipo/{idEquipo}/temporada")
    public void asociarTemporadaConEquipo(@PathVariable Integer idEquipo, @RequestBody List<Integer> idTemporada) {
        equipoService.associateTemporadasWithTeam(idEquipo, idTemporada);
    }

    @DeleteMapping("/eliminar/{idEquipo}")
    public void eliminarEquipo(@PathVariable Integer idEquipo) {
        equipoService.delete(idEquipo);
    }

}
