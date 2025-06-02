package com.example.zonadegolbackend.services;


import com.example.zonadegolbackend.entity.Noticias;
import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.enums.Rol;
import com.example.zonadegolbackend.repository.NoticiasRepository;
import com.example.zonadegolbackend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoticiasService {

    private final NoticiasRepository noticiasRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Noticias> findAll() {
        return noticiasRepository.findAll();
    }

    public Noticias crearNoticia(Noticias noticia) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede crear noticias");
        }

        Noticias noticiaNueva = new Noticias();
        noticiaNueva.setImagen(noticia.getImagen());
        noticiaNueva.setTitulo(noticia.getTitulo());
        noticiaNueva.setDescripcion(noticia.getDescripcion());

        return noticiasRepository.save(noticiaNueva);
    }

    public Noticias editarNoticia(Integer id, Noticias noticia) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede editar noticias");
        }

        Noticias noticiaExistente = noticiasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Noticia no encontrada"));

        noticiaExistente.setImagen(noticia.getImagen());
        noticiaExistente.setTitulo(noticia.getTitulo());
        noticiaExistente.setDescripcion(noticia.getDescripcion());

        return noticiasRepository.save(noticiaExistente);
    }

    public void eliminarNoticia(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != Rol.ADMIN) {
            throw new RuntimeException("Solo un administrador puede eliminar noticias");
        }

        if (!noticiasRepository.existsById(id)) {
            throw new RuntimeException("Noticia no encontrada");
        }

        noticiasRepository.deleteById(id);
    }

    public List<Noticias> findTop3() {
        return noticiasRepository.findTop3ByOrderByIdDesc();
    }

}
