package com.example.zonadegolbackend.controller;

import com.example.zonadegolbackend.dtos.CorreoAdminDTO;
import com.example.zonadegolbackend.dtos.EntrenadorDTO;
import com.example.zonadegolbackend.dtos.CrearEquipo;
import com.example.zonadegolbackend.dtos.CrearJugador;
import com.example.zonadegolbackend.entity.Entrenador;
import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.entity.Jugador;
import com.example.zonadegolbackend.services.EntrenadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/entrenador")
public class EntrenadorController {


    private final EntrenadorService entrenadorService;

    @GetMapping("/listar")
    public List<EntrenadorDTO> listarEntrenador() {
        return entrenadorService.listarEntrenador();
    }


    @PostMapping("/crear")
    public Entrenador crearEntrenador(@RequestBody EntrenadorDTO entrenadorDTO) {
        return entrenadorService.create(entrenadorDTO);
    }

    @PutMapping("/editar/{idEntrenador}")
    public Entrenador editarEntrenador(@PathVariable Integer idEntrenador, @RequestBody EntrenadorDTO entrenadorDTO) {
        return entrenadorService.update(idEntrenador, entrenadorDTO);
    }

    @DeleteMapping("/eliminar/{idEntrenador}")
    public void eliminarEntrenador(@PathVariable Integer idEntrenador) {
        entrenadorService.delete(idEntrenador);
    }

    @PostMapping("/crear/equipo/{idLiga}")
    public Equipo crearEquipo(@RequestBody CrearEquipo crearEquipo,@PathVariable Integer idLiga) {
       return entrenadorService.createEquipo(crearEquipo,idLiga);
    }


    @PostMapping("/crear/jugador")
    public Jugador createJugador(@RequestBody CrearJugador crearJugador) {
        return entrenadorService.createJugador(crearJugador);
    }
    @PutMapping("/editar/jugador/{idJugador}")
    public Jugador updateJugador(@RequestBody CrearJugador crearJugador, @PathVariable Integer idJugador) {
        return entrenadorService.updateJugador(crearJugador, idJugador);

    }

    @PutMapping("/jugador/activo/{id}")
    public Jugador cambiarEstadoActivoJugador(
            @PathVariable Integer id) {
        return entrenadorService.cambiarEstadoActivoJugador(id);

    }

    @PutMapping("/editar/equipo")
    public Equipo editarEquipo(@RequestBody CrearEquipo equipo) {
        return entrenadorService.updateEquipo(equipo);
    }


    @PostMapping("/enviar-correo-admin")
    public void enviarCorreoAdmin(@RequestBody CorreoAdminDTO request) {
        entrenadorService.enviarCorreoAdmin(request.getAsunto(), request.getContenido());
    }

}
