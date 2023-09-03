package com.prueba06.controller;

import com.prueba06.entity.UsuarioEntity;
import com.prueba06.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    
    @GetMapping
    public String paginainicio(){
        return "index.html";
    }
    
    /* RUTA PARA INICIAR SESION */
    @GetMapping("/login")
    public String iniciarSesion(){
        return "login.html";
    }
    
    // RUTA PARA CERRAR SESION
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate(); // Invalidar la sesión actual
        }
        return "redirect:/login?logout"; // Redirigir a la página de inicio de sesión con un mensaje de cierre de sesión
    }
    
    
    
    @Autowired
    private UsuarioService usuarioService;

    /* PERFIL DEL USUARIO ACTUAL... */
    @GetMapping("/perfil")
    public String mostrarPerfil(Model model, Authentication authentication){
        // Obtener el nombre de usuario (correo electrónico) del objeto de autenticación
        String username = authentication.getName();

        // Cargar los detalles del usuario desde la base de datos
        UsuarioEntity usuario = usuarioService.obtenerUsuarioPorEmail(username);

        // Agregar los detalles del usuario al modelo para su visualización en la vista
        model.addAttribute("nombres", usuario.getNombre());
        model.addAttribute("apellidos", usuario.getApellido());

        return "perfil"; // Nombre de la vista Thymeleaf que muestra el perfil del usuario
    }
    
    
}

















