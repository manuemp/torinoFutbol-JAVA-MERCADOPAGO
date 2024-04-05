package com.proyecto.torinofutbol.Models;

import jakarta.transaction.Transactional;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.email = ?1")
    Usuario findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE usuarios SET racha = racha + 1 WHERE id = ?1", nativeQuery = true)
    public void aumentarRacha(Long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE usuarios SET racha = 0 WHERE id = ?1", nativeQuery = true)
    public void reiniciarRacha(Long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE usuarios SET racha = racha - 1 WHERE id = ?1", nativeQuery = true)
    public void bajarRacha(Long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE usuarios SET faltas = faltas + 1 WHERE id = ?1", nativeQuery = true)
    public void aplicarFalta(Long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE usuarios SET faltas = 0 WHERE id = ?1 AND faltas > 3", nativeQuery = true)
    public void reiniciarFaltas(Long id);

}
