package com.example.zonadegolbackend.repository;

import com.example.zonadegolbackend.entity.TokenAcceso;
import com.example.zonadegolbackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenAcceso,Integer> {
    TokenAcceso findTopByUsuario(Usuario usuario);

}
