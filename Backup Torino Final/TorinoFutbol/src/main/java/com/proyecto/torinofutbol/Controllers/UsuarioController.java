package com.proyecto.torinofutbol.Controllers;

import com.proyecto.torinofutbol.Models.Reserva;
import com.proyecto.torinofutbol.Models.ReservasRepository;
import com.proyecto.torinofutbol.Models.Usuario;
import com.proyecto.torinofutbol.Models.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    ReservasRepository reservasRepo;
    @Autowired
    UsuarioRepository usuariosRepo;

    @GetMapping("/obtenerDatosUsuarioActual")
    public Usuario getDatosUsuarioActual(Principal principal){
        return usuariosRepo.findByEmail(principal.getName());
    }

    @GetMapping("/obtenerDatosUsuarioEmail/{email}")
    public Usuario getDatosUsuarioEmail(@PathVariable("email") String email) {
        return usuariosRepo.findByEmail(email);
    }

    @RequestMapping(value = "/obtenerDatosUsuarioID", method = RequestMethod.POST)
    public Optional<Usuario> getDatosUsuario(@RequestBody Map<String, String> usuario){
        Long id = Long.parseLong(usuario.get("id"));
        return usuariosRepo.findById(id);
    }

    @GetMapping("/obtenerCantidadReservasUsuario")
    public int getCantidadReservasUsuario(Principal principal){
        Usuario usuario = usuariosRepo.findByEmail(principal.getName());
        return reservasRepo.findAllByUserId(usuario.getId()).size();
    }

    @GetMapping("/obtenerHistorialReservasUsuario")
    public List<Reserva> getHistorialReservasUsuario(Principal principal){
        Usuario usuario = usuariosRepo.findByEmail(principal.getName());
        return reservasRepo.findAllByUserId(usuario.getId());
    }

    @RequestMapping(value = "/aplicarFalta", method = RequestMethod.POST)
    public void aplicarFaltaUsuario(@RequestBody Map<String, String> datos){
        Long idUsuario = Long.parseLong(datos.get("idUsuario"));
        Long idReserva = Long.parseLong(datos.get("idReserva"));
        Reserva reserva = reservasRepo.findByReservaID(idReserva);

        //En caso de que sin querer se haya indicado que el usuario asistió
        //pero luego se quiere indicar que faltó, cambio el estado de la reserva,
        //bajo la racha que había aumentado y recién después aplico la falta
        if(reserva.getAsistio() == 1){
            reserva.setAsistio(-1);
            usuariosRepo.bajarRacha(idUsuario);
        }

        usuariosRepo.aplicarFalta(idUsuario);
        usuariosRepo.reiniciarFaltas(idUsuario);
    }

//    @RequestMapping(value = "/aumentarRacha", method = RequestMethod.POST)
//    public void aumentarRachaUsuario(@RequestBody Map<String, String> usuario){
//        Long id = Long.parseLong(usuario.get("id"));
//        usuariosRepo.aumentarRacha(id);
//    }

//    @RequestMapping(value = "/obtenerFaltasUsuario", method = RequestMethod.POST)
//    public Long getFaltasUsuario(@RequestBody Map<String, String> usuario){
//        Long id = Long.parseLong(usuario.get("id"));
//        return usuariosRepo.getFaltas(id);
//    }
}
