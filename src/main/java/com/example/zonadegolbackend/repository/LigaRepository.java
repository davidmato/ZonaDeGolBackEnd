package com.example.zonadegolbackend.repository;


import com.example.zonadegolbackend.entity.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface LigaRepository extends JpaRepository<Liga, Integer> {
}
