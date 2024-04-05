package com.proyecto.torinofutbol.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 40)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 40)
    private String apellido;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "pass", nullable = false, length = 64)
    private String pass;

    @Column(name = "telefono", nullable = true, length = 10)
    private String telefono;

    @Column(name = "reservas", nullable = true)
    private int reservas;

    @Column(name = "faltas", nullable = true)
    private int faltas;

    @Column(name = "racha", nullable = true)
    private int racha;

    @Column(name = "rol", nullable = true)
    private String rol = "USER";

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> Listareservas;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getReservas() {
        return reservas;
    }

    public void setReservas(int reservas) {
        this.reservas = reservas;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public int getRacha() {
        return racha;
    }

    public void setRacha(int racha) {
        this.racha = racha;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

}
