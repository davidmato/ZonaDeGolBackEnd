package com.example.zonadegolbackend.repository;


import com.example.zonadegolbackend.entity.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface LigaRepository extends JpaRepository<Liga, Integer> {
}
