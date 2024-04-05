package com.proyecto.torinofutbol.Models;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
@Transactional
public interface ReservasRepository extends JpaRepository<Reserva, Long> {

    @Query(value = "SELECT * FROM reservas WHERE usuario_id = ?1 ORDER BY dia DESC", nativeQuery = true)
    public List<Reserva> findAllByUserId(Long userId);

    @Query(value = "SELECT * FROM reservas WHERE id = ?1", nativeQuery = true)
    public Reserva findByReservaID(Long userId);

    @Query("SELECT r FROM Reserva r WHERE r.usuario.email = ?1 ORDER BY r.dia DESC")
    public List<Reserva> findAllByEmail(String email);

    @Query(value = "SELECT * FROM reservas WHERE dia >= ?1 ORDER BY dia ASC, hora ASC", nativeQuery = true)
    public List<Reserva> findAllSinceDate(LocalDate hoy);

    @Query(value = "SELECT * FROM reservas WHERE dia = ?1 AND cancha_id =?2", nativeQuery = true)
    public List<Reserva> findAllByDiaCancha(LocalDate dia, String idCancha);

    @Query(value = "SELECT * FROM reservas WHERE dia = ?1 AND hora = ?2 AND cancha_id = ?3", nativeQuery = true)
    public Reserva checkReserva(LocalDate dia, String hora, Long cancha);

    @Query(value = "SELECT * FROM reservas WHERE dia >= ?1 AND usuario_id = ?2", nativeQuery = true)
    public List<Reserva> findPendientesUsuario(LocalDate dia, String idUsuario);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE reservas SET asistio = ?1 WHERE id = ?2", nativeQuery = true)
    public void setAsistio(int asistio, Long id);

}
