package com.proyecto.torinofutbol.Models;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dia", nullable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate dia;

    @Column(name = "hora", nullable = false)
    private String hora;

    @Column(name = "asistio", nullable = false)
    private int asistio;

    @Column(name = "dia_de_reserva", nullable = false)
    private LocalDate diaDeReserva;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cancha_id")
    private Cancha cancha;

    private Long id_cancha;



    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }


    public Long getId_cancha() {
        return id_cancha;
    }

    public void setId_cancha(Long id_cancha) {
        this.id_cancha = id_cancha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getAsistio() {
        return asistio;
    }

    public void setAsistio(int asistio) {
        this.asistio = asistio;
    }

    public LocalDate getDiaDeReserva() {
        return diaDeReserva;
    }

    public void setDiaDeReserva(LocalDate diaDeReserva) {
        this.diaDeReserva = diaDeReserva;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cancha getCancha() {
        return cancha;
    }

    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }
}
