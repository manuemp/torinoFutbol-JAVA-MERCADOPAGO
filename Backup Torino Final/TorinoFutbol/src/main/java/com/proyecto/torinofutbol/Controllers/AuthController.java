package com.proyecto.torinofutbol.Controllers;

import com.proyecto.torinofutbol.Models.*;
import com.proyecto.torinofutbol.Service.CustomUserDetailsService;
import com.proyecto.torinofutbol.Service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(method = {
        RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT
})
@SessionAttributes("reservas")
public class AuthController {

    @Autowired
    public SessionService sessionService;

    @Autowired
    private UsuarioRepository repoUsuarios;

    @Autowired
    private AdminRepository repoAdmins;

    @Autowired
    private ReservasRepository repoReservas;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;


    @GetMapping(value = {"", "/"})
    public String paginaIndex(Principal principal, HttpServletRequest request){
        // false = Obtener la sesión actual sin crear una nueva si no existe
        if (principal != null) {
            return "redirect:/home";
        } else {
            return "index";
        }
    }

    @GetMapping("/loginPage")
    public String login(HttpServletRequest request){

        //Elimino la sesión,
        // por alguna razón se crea automáticamente una sesión de Apache al entrar al login
        HttpSession session = request.getSession(false);
        session.invalidate();

        return "login_form";
    }

    @GetMapping("/home")
    public String loguearUsuario(Principal principal, RedirectAttributes ra, Model model, HttpServletRequest request){

        Usuario usuario = repoUsuarios.findByEmail(principal.getName());
        Admin admin = repoAdmins.findByEmail(principal.getName());

        if(usuario != null) {
            model.addAttribute("usuario", usuario);
            return "logged_user";
        }
        if(admin != null)
            return "redirect:/admin/reservas";

        ra.addFlashAttribute("mensaje_error", "Hubo un problema con el inicio de sesión. Intente nuevamente");
        return "index";
    }

    @GetMapping("/register")
    public String formularioRegistro(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        session.invalidate();

        model.addAttribute("user", new Usuario());
        return "register_form";
    }


    @GetMapping("/process_register")
    public String procesarRegistro(Usuario user, HttpServletRequest request,
                                   HttpServletResponse response,
                                   RedirectAttributes ra, @RequestParam("repetirPass") String repetirPass ){
//
//        if(user.getEmail() == null || user.getPass() == null || user.getNombre() == null
//            user.getApellido() == null || user.)

        //Verifico que no esté usado el mail
        if(repoUsuarios.findByEmail(user.getEmail()) != null) {
            ra.addFlashAttribute("mensaje_error", "Correo electrónico en uso");
            return "redirect:/register";
        }

        //Verifico que las contraseñas coincidan
        if(!(user.getPass().equals(repetirPass))){
            ra.addFlashAttribute("mensaje_error", "Las contraseñas no coinciden!");
            return "redirect:/register";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPass = encoder.encode(user.getPass());
        user.setPass(encodedPass);
        //Todos los usuarios nuevos se loguean como USER
        user.setRol("USER");
        user.setFaltas(0);
        user.setRacha(0);
        user.setReservas(0);
        user.setTelefono("Sin Número");

        repoUsuarios.save(user);
        crearSesion(user, request, response);
        return "redirect:/home";
    }

    private void crearSesion(Usuario user, HttpServletRequest request, HttpServletResponse response){
        //Obtengo el UserDetails para la autenticación
        var userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        //Creo una autenticación con el UserDetails recién creado
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        //Obtengo un ContextHolderStrategy, el cual me va a permitir crear un Context vacío
        SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
                .getContextHolderStrategy();

        //Creo el context vacío y le aplico la autenticación
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        //Agrego el contexto creado con la autenticación al SecurityContext general del proyecto
        securityContextHolderStrategy.setContext(context);

        //Guardo en el bean el contexto, que sería guardar la autenticación en la sesión del usuario
        securityContextRepository.saveContext(context, request, response);
    }

}
