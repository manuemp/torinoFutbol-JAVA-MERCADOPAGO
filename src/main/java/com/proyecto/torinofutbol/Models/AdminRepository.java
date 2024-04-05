package com.proyecto.torinofutbol.Models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("SELECT u FROM Admin u WHERE u.email = ?1")
    Admin findByEmail(String email);
}
