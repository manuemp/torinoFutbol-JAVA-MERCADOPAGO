package com.proyecto.torinofutbol.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @Column(name = "pass", nullable = false, length = 255)
    private String pass;

    private String rol = "ADMIN";

    public Admin() {
    }

    public Admin(String email, String pass, String rol) {
        this.email = email;
        this.pass = pass;
        this.rol = rol;
    }

    public Admin(Long id, String email, String pass, String rol) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
