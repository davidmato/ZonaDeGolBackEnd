package com.example.zonadegolbackend.repository;


import com.example.zonadegolbackend.entity.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LigaRepository extends JpaRepository<Liga, Integer> {
    @Query("SELECT l FROM Liga l WHERE l.id = :id")
    Liga findLigaById(@Param("id") Integer id);

    @Query(value = "SELECT * FROM zona_de_gol.liga ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Liga findRandomLiga();
}
