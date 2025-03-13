package com.example.zonadegolbackend.services;

import com.example.zonadegolbackend.entity.Equipo;
import com.example.zonadegolbackend.repository.EquipoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EquipoService {

    private final EquipoRepository equipoRepository;

    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

}
