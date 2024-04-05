package com.proyecto.torinofutbol.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "niveles")
public class Nivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name ="nombre", nullable = false)
    private String nombre;

    @Column(name = "cantidad_reservas", nullable = false)
    private int cantidadReservas;

    @Column(name = "descuento", nullable = false)
    private double descuento;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidadReservas() {
        return cantidadReservas;
    }

    public void setCantidadReservas(int cantidadReservas) {
        this.cantidadReservas = cantidadReservas;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
