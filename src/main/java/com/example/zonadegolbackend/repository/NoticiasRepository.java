package com.example.zonadegolbackend.repository;


import com.example.zonadegolbackend.entity.Noticias;
import com.example.zonadegolbackend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticiasRepository extends JpaRepository<Noticias, Integer> {

    List<Noticias> findTop3ByOrderByIdDesc();

}
