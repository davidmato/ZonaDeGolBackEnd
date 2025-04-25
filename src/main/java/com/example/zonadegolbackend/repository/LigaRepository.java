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
}
