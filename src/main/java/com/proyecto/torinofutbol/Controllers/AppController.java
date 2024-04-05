package com.proyecto.torinofutbol.Controllers;

import com.proyecto.torinofutbol.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class AppController {

    @Autowired
    ReservasRepository reservasRepo;
    @Autowired
    UsuarioRepository usuariosRepo;
    @Autowired
    CanchasRepository canchasRepo;
    @Autowired
    NivelesRepository nivelesRepo;


//    private LocalDateTime ahora = LocalDateTime.now();
//    private ZonedDateTime hoy = ahora.atZone(ZoneId.of("America/Argentina/Buenos_Aires"));
    LocalDate hoy = LocalDate.now(ZoneId.of("America/Argentina/Buenos_Aires"));

    @GetMapping("/hacerReserva")
    public String hacerReserva(Model model, RedirectAttributes ra, Principal principal){

        Usuario usuario = usuariosRepo.findByEmail(principal.getName());

        //Si el usuario tiene más de 3 reservas pendientes no lo dejo hacer otra
        if(reservasRepo.findPendientesUsuario(hoy, usuario.getId().toString()).size() >= 3){
            ra.addFlashAttribute("mensaje_error_reserva", "No podés tener más de 3 reservas pendientes!");
            return "redirect:/home";
        }
        Map<LocalDate, String> dias = generarSemana();
        Nivel nivel = getNivelByRacha(usuario.getRacha());

        model.addAttribute("reserva", new Reserva());
        model.addAttribute("nivel", nivel);
        model.addAttribute("dias", dias);
        return "reserva_form";
    }

    @PostMapping("/generarReserva")
    public String generarReserva(@ModelAttribute("reserva") Reserva reserva, Principal principal, RedirectAttributes ra){

        Usuario usuario = usuariosRepo.findByEmail(principal.getName());
        Cancha cancha = canchasRepo.findByIdCancha(reserva.getId_cancha());

        //Si la reserva ya existe redirecciono nuevamente a hacer otra reserva
        if(reservasRepo.checkReserva(reserva.getDia(), reserva.getHora(), reserva.getId_cancha()) != null){
            ra.addFlashAttribute("mensaje_error_reserva", "Esta reserva ya fue ocupada. Intentá con otra");
            return "redirect:/hacerReserva";
        }

        reserva.setCancha(cancha);
        reserva.setDiaDeReserva(hoy);
        reserva.setAsistio(0);
        reserva.setUsuario(usuario);
        reserva.setPrecio(calcularPrecioReserva(cancha, usuario));

        try{
//            usuariosRepo.aumentarRacha(usuario.getEmail());
            reservasRepo.save(reserva);
            ra.addFlashAttribute("mensaje_exito_reserva", "La reserva fue realizada con éxito!");
            return "redirect:/home";
        }catch(Exception e) {
            ra.addFlashAttribute("mensaje_error_reserva", "Hubo un error al intentar realizar la reserva...");
            return "redirect:/hacerReserva";
        }
    }

    @PostMapping("/errorReserva")
    public String errorReserva(RedirectAttributes ra){
        ra.addFlashAttribute("mensaje_error_reserva", "Hubo un error en las credenciales de la tarjeta");
        return "redirect:/hacerReserva";
    }

    @GetMapping("/reservasUsuario")
    public String reservasUsuario(Principal principal, Model model){
        Usuario usuario = usuariosRepo.findByEmail(principal.getName());
        List<Reserva> reservasUsuario = reservasRepo.findAllByUserId(usuario.getId());

        model.addAttribute("usuario", usuario);
        model.addAttribute("reservas", reservasUsuario);

        return "reservas_usuario";
    }

    @GetMapping("/admin/reservas")
    public String reservas(Model model){
//        List<LocalDate> dias = new ArrayList<>();
//        dias = generarSemana();
        Map<LocalDate, String> dias = new LinkedHashMap<>();
        dias = generarSemana();
        model.addAttribute("dias", dias);
        return "reservas_admin";
    }

    //En los select necesito tener como value la hora en formato de SQL 'yyyy/MM/dd'
    //Pero en el innerHTML es mejor tenerlo como 'dd/MM/yyyy'
    //Para eso genero un Map<formatoSQL, formatoArg> y lo paso a thymeleaf
    private Map<LocalDate, String> generarSemana(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Map<LocalDate, String> dias = new LinkedHashMap<>();

        //Por alguna razón Thymeleaf los presenta al revés, así que los agrego de mayor a menor
        dias.put(hoy, hoy.format(formato));
        dias.put(hoy.plusDays(1), hoy.plusDays(1).format(formato));
        dias.put(hoy.plusDays(2), hoy.plusDays(2).format(formato));
        dias.put(hoy.plusDays(3), hoy.plusDays(3).format(formato));
        dias.put(hoy.plusDays(4), hoy.plusDays(4).format(formato));
        dias.put(hoy.plusDays(5), hoy.plusDays(5).format(formato));
        dias.put(hoy.plusDays(6), hoy.plusDays(6).format(formato));

        return dias;
    }


    public Double calcularPrecioReserva(Cancha cancha, Usuario usuario){

        Double precio = cancha.getPrecio();
        Nivel nivel = getNivelByRacha(usuario.getRacha());
        Double descuento = nivel.getDescuento();

        return precio - precio * descuento;
    }

    public Nivel getNivelByRacha(int reservas){
        List<Nivel> niveles = nivelesRepo.findAll();

        Long nivelId = 0L;
        for (Nivel nivel :niveles) {
            if(reservas >= nivel.getCantidadReservas())
                nivelId++;
            else break;
        }
        return nivelesRepo.getNivelById(nivelId);
    }

    public int getCantidadReservas(Principal principal){
        return reservasRepo.findAllByEmail(principal.getName()).size();
    }

    @GetMapping("/contacto")
    public String paginaContacto(Model model, Principal principal){

        model.addAttribute("mensaje", new Mensaje());

        if(principal != null) {
            return "contacto_online";
        }
        return "contacto_offline";
    }


//    private List<LocalDate> generarSemana(){
//        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/mm/yyyy");
//        List<LocalDate> dias = new ArrayList<>();
//
//        dias.add(hoy);
//        dias.add(hoy.plusDays(1));
//        dias.add(hoy.plusDays(2));
//        dias.add(hoy.plusDays(3));
//        dias.add(hoy.plusDays(4));
//        dias.add(hoy.plusDays(5));
//        dias.add(hoy.plusDays(6));
//
//        return dias;
//    }
}
