package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Arbitro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArbitroRepository extends JpaRepository<Arbitro, Integer> {
    @Query("SELECT a FROM Arbitro a WHERE a.usuario.username = :username")
    Optional<Arbitro> findByUsuarioUsername(@Param("username") String username);

    Arbitro findByUsuario_Id(Integer usuarioId);
}
