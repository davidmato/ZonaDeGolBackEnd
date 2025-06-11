package com.example.zonadegolbackend.services;


import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.entity.Trofeo;
import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.LigaRepository;
import com.example.zonadegolbackend.repository.TrofeoRepository;
import com.example.zonadegolbackend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrofeoService {

    private TrofeoRepository trofeoRepository;
    private UsuarioRepository usuarioRepository;

    public List<Trofeo> findAll() {return trofeoRepository.findAll();}

    public Trofeo crearTrofeo(Trofeo trofeo){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioAutenticado.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede crear trofeos");
        }

        Trofeo nuevoTrofeo = new Trofeo();

        nuevoTrofeo.setNombre(trofeo.getNombre());
        nuevoTrofeo.setImagen(trofeo.getImagen());
        nuevoTrofeo.setTemporadaLiga(trofeo.getTemporadaLiga());

        return trofeoRepository.save(nuevoTrofeo);
    }

    public Trofeo buscarTrofeoPorTemporadaLiga(Integer temporadaLigaId) {
        return trofeoRepository.findByTemporadaLigaId(temporadaLigaId);
    }
}
