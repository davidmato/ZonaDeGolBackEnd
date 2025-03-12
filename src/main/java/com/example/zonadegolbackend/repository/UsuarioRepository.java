package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByCorreo(String email);

    Optional<Usuario> findTopByUsername(String username);
    Optional<Usuario> findFirstByUsername(String username);
}
