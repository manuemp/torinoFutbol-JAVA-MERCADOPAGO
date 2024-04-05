package com.proyecto.torinofutbol.Service;

import com.proyecto.torinofutbol.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repoUsuarios;
    @Autowired
    private AdminRepository repoAdmins;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repoUsuarios.findByEmail(email);
        Admin admin = repoAdmins.findByEmail(email);

        if(usuario != null)
            return new CustomUserDetails(usuario);
        if(admin != null)
            return new CustomUserDetails(admin);

        throw new UsernameNotFoundException("No se ha encontrado el usuario");
    }
}
