package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String email);

    Optional<Usuario> findTopByUsername(String username);
    Optional<Usuario> findFirstByUsername(String username);
    Optional<Usuario> findByUsername(String username);

    List<Usuario> findAllByRol(Rol rol);

    Optional<Usuario> findByTokenRestablecimiento(String tokenRestablecimiento);

    List<Usuario> findByPagadoFalseAndFechaRegistroBefore(LocalDateTime fecha);

}
