package com.example.zonadegolbackend.services;


import com.example.zonadegolbackend.entity.Temporada;
import com.example.zonadegolbackend.entity.Trofeo;
import com.example.zonadegolbackend.repository.LigaRepository;
import com.example.zonadegolbackend.repository.TrofeoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrofeoService {

    private TrofeoRepository trofeoRepository;

    public List<Trofeo> findAll() {return trofeoRepository.findAll();}

    public void crearTrofeo(Trofeo trofeo){

        Trofeo nuevoTrofeo = new Trofeo();

        nuevoTrofeo.setNombre(trofeo.getNombre());
        nuevoTrofeo.setImagen(trofeo.getImagen());

        trofeoRepository.save(nuevoTrofeo);
    }
}
