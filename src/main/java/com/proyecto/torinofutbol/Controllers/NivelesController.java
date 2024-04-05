package com.proyecto.torinofutbol.Controllers;

import com.proyecto.torinofutbol.Models.Nivel;
import com.proyecto.torinofutbol.Models.NivelesRepository;
import com.proyecto.torinofutbol.Models.Usuario;
import com.proyecto.torinofutbol.Models.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class NivelesController{

    @Autowired
    private NivelesRepository nivelesRepo;

    @Autowired
    private UsuarioRepository usuariosRepo;

    @RequestMapping("/getNivel/{reserva}")
    public Nivel getNivelByCantidadReservas(@PathVariable(value = "reserva") int reservas){
        Long id = getNivel(reservas);
        return nivelesRepo.getNivelById(id);
    }

    @RequestMapping("/getNivelUsuario")
    public Nivel getNivelUsuario(Principal principal){
        Usuario usuario = usuariosRepo.findByEmail(principal.getName());
        int rachaUsuario = usuario.getRacha();

        return nivelesRepo.getNivelById(getNivel(rachaUsuario));
    }

    private Long getNivel(int cantidadReservas){
        List<Nivel> niveles = nivelesRepo.findAll();

        Long nivelId = 0L;
        for (Nivel nivel :niveles) {
            if(cantidadReservas > nivel.getCantidadReservas())
                nivelId++;
            else break;
        }
        return nivelId;
    }

}
