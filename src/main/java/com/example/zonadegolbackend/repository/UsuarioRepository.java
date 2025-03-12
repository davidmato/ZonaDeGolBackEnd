package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByCorreo(String email);

    Optional<Usuario> findTopByUsername(String username);
    Optional<Usuario> findFirstByUsername(String username);
}
