package com.proyecto.torinofutbol.Models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private Usuario usuario;
    private Admin admin;

    private int sessionId = 19;

    public CustomUserDetails(Usuario usuario) {

        this.usuario = usuario;
        this.sessionId = 18;

    }
    public CustomUserDetails(Admin admin) {
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> autoridades = new ArrayList<>();
        if(usuario != null)
            autoridades.add(new SimpleGrantedAuthority(usuario.getRol()));
        else if (admin != null)
            autoridades.add(new SimpleGrantedAuthority(admin.getRol()));

        return autoridades;
    }

    @Override
    public String getPassword() {
        if(usuario != null)
            return usuario.getPass();
        else return admin.getPass();

    }

    @Override
    public String getUsername() {
        if(usuario != null)
            return usuario.getEmail();
        else return admin.getEmail();
    }

    public int getSessionId(){
        return sessionId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}