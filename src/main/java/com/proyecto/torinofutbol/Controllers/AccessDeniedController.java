package com.proyecto.torinofutbol.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccessDeniedController {

    @GetMapping("/denied")
    public String deniedAccessController(RedirectAttributes ra){
        ra.addFlashAttribute("mensaje_excepcion", "¡No podés acceder a esta página! O no existe, o no tenés los permisos suficientes.");
        return "redirect:/";
    }

}
