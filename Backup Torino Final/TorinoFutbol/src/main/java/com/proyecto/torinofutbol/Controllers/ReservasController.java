package com.proyecto.torinofutbol.Controllers;

import com.proyecto.torinofutbol.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController

public class ReservasController {

    private LocalDate hoy = LocalDateTime.now().atZone(ZoneId.of("America/Argentina/Buenos_Aires")).toLocalDate();
    @Autowired
    ReservasRepository reservasRepo;
    @Autowired
    UsuarioRepository usuariosRepo;
    @Autowired
    NivelesRepository nivelesRepo;
    @Autowired
    CanchasRepository canchasRepo;



    @GetMapping("/reservasPendientes")
    public List<Reserva> reservasPendientes(){
        return reservasRepo.findAllSinceDate(hoy);
    }

    @GetMapping("/reservasPendientesUsuario")
    public List<Reserva> getReservasPendientesUsuario(Principal principal) {
        Usuario usuario = usuariosRepo.findByEmail(principal.getName());
        return reservasRepo.findPendientesUsuario(hoy, usuario.getId().toString());
    }

    @RequestMapping(value = "/obtenerReservasDia", method = RequestMethod.POST)
    public List<Reserva> getReservasDia(@RequestBody Map<String, String> datos){
        LocalDate diaReserva = LocalDate.parse(datos.get("dia"));
        return reservasRepo.findAllByDiaCancha(diaReserva, datos.get("cancha"));
    }

    @RequestMapping(value="/obtenerReservasEmail", method = RequestMethod.POST)
    public List<Reserva> getReservasEmail(@RequestBody Map<String, String> datos){
        String email = datos.get("email");
        return reservasRepo.findAllByEmail(email);
    }

    @RequestMapping(value="/obtenerReservaID", method = RequestMethod.POST)
    public Reserva getReservaID(@RequestBody Map<String, String> datos){
        Long id;
        try{
            id = Long.parseLong(datos.get("id"));
            return reservasRepo.findByReservaID(id);
        }
        catch(Exception e){
            return null;
        }
    }

    @RequestMapping(value="/eliminarReserva", method = RequestMethod.POST)
    public void eliminarReserva(@RequestBody Map<String, String> datos){
        Long id = Long.parseLong(datos.get("id"));
        Reserva reserva = reservasRepo.findByReservaID(id);
        Usuario usuario = reserva.getUsuario();

        //Si por equivocacion se había hecho click en "Asistió" y lo que
        //había que hacer era eliminar la reserva, al eliminarla se baja la racha
        //que había aumentado con "Asistió".
        if(reserva.getAsistio() == 1)
            usuariosRepo.bajarRacha(usuario.getId());
        reservasRepo.deleteById(id);
    }

    @RequestMapping(value = "/setAsistio", method = RequestMethod.POST)
    public void setAsistio(@RequestBody Map<String, String> datos){
        Long idReserva = Long.parseLong(datos.get("reserva"));
        Long idUsuario = Long.parseLong(datos.get("usuario"));
        int asistio = Integer.parseInt(datos.get("asistio"));

        reservasRepo.setAsistio(asistio, idReserva);
        if(asistio == 1)
            usuariosRepo.aumentarRacha(idUsuario);
    }

    @RequestMapping(value = "/obtenerPrecioFinal", method = RequestMethod.POST)
    public Double getPrecioFinalReserva(@RequestBody Map<String, String> datosReserva){

        Long idCancha = Long.parseLong(datosReserva.get("idCancha"));
        Cancha cancha = canchasRepo.findByIdCancha(idCancha);

        return cancha.getPrecio();
    }


}
