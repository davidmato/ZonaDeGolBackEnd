package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.Arbitro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArbitroRepository extends JpaRepository<Arbitro, Integer> {

    Arbitro findByUsuario_Username(String username);


    Arbitro findByUsuario_Id(Integer usuarioId);
}
