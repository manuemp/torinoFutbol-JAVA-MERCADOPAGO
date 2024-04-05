package com.proyecto.torinofutbol.Models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface NivelesRepository extends JpaRepository<Nivel, Long> {
    @Query(value = "SELECT * FROM niveles WHERE id = ?1", nativeQuery = true)
    public Nivel getNivelById(Long id);
}
