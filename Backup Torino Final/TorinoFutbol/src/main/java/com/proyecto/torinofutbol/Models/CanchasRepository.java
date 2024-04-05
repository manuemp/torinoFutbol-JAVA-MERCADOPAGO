package com.proyecto.torinofutbol.Models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CanchasRepository extends JpaRepository<Cancha, Long> {
    @Query("SELECT c FROM Cancha c WHERE c.id = ?1")
    Cancha findByIdCancha(Long id);

    @Query("SELECT c.precio FROM Cancha c WHERE c.id = ?1")
    int getPrecioById(Long id);
}
