package com.example.zonadegolbackend.repository;


import com.example.zonadegolbackend.entity.Liga;
import com.example.zonadegolbackend.entity.Trofeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrofeoRepository extends JpaRepository<Trofeo, Integer> {
}
