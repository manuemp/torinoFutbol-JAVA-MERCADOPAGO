package com.proyecto.torinofutbol.Controllers;

import com.proyecto.torinofutbol.Models.Mensaje;
import com.proyecto.torinofutbol.Service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/mail")
public class EmailController {

    private EmailService emailService;

    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String enviarEmail(Mensaje mensaje, RedirectAttributes ra) {

        String asunto = "Consulta de " + mensaje.getNombre() + " - " + mensaje.getEmail();

        try{
            emailService.enviarEmail(asunto, mensaje.getMensaje());
            ra.addFlashAttribute("mensaje_exito", "El mail se envió correctamente! Contestaremos lo antes posible!");
            return "redirect:/contacto";
        }catch(Exception e){
            ra.addFlashAttribute("mensaje_error_mail", "Hubo un error al enviar el mail! Intentá nuevamente!");
            return "redirect:/home";
        }
    }
}
