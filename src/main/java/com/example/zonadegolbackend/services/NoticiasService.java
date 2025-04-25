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

    public Noticias crearNoticia(Noticias noticia) {

        Noticias noticiaNueva = new Noticias();

        noticiaNueva.setImagen(noticia.getImagen());
        noticiaNueva.setTitulo(noticia.getTitulo());
        noticiaNueva.setDescripcion(noticia.getDescripcion());

        return noticiasRepository.save(noticiaNueva);
    }

    public Noticias editarNoticia(Integer id, Noticias noticia) {
        Noticias noticiaExistente = noticiasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));

        noticiaExistente.setImagen(noticia.getImagen());
        noticiaExistente.setTitulo(noticia.getTitulo());
        noticiaExistente.setDescripcion(noticia.getDescripcion());

        return noticiasRepository.save(noticiaExistente);
    }

    public void eliminarNoticia(Integer id) {
        if (!noticiasRepository.existsById(id)) {
            throw new RuntimeException("Noticia no encontrada");
        }
        noticiasRepository.deleteById(id);
    }
}
