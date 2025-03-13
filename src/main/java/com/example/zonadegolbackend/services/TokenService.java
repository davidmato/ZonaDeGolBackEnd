package com.example.zonadegolbackend.services;


import com.example.zonadegolbackend.entity.TokenAcceso;
import com.example.zonadegolbackend.entity.Usuario;
import com.example.zonadegolbackend.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenService {

    private TokenRepository tokenRepositorio;


    public TokenAcceso getByUsuario(Usuario usuario){
        return tokenRepositorio.findTopByUsuario(usuario);
    }

    public TokenAcceso save(TokenAcceso token){
        return tokenRepositorio.save(token);
    }

}
