package com.example.zonadegolbackend.services;


import com.example.zonadegolbackend.entity.Noticias;
import com.example.zonadegolbackend.repository.NoticiasRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoticiasService {

    private final NoticiasRepository noticiasRepository;

    public List<Noticias> findAll() {
        return noticiasRepository.findAll();
    }

    public void crearNoticia(Noticias noticia) {

        Noticias noticiaNueva = new Noticias();

        noticiaNueva.setImagen(noticia.getImagen());
        noticiaNueva.setTitulo(noticia.getTitulo());
        noticiaNueva.setDescripcion(noticia.getDescripcion());

        noticiasRepository.save(noticiaNueva);
    }
}
