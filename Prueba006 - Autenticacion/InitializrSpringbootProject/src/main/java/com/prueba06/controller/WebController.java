package com.prueba06.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    
    @GetMapping
    public String paginainicio(){
        return "index.html";
    }
    
    @GetMapping("/login")
    public String ingresoalsistema(){
        return "login.html";
    }
    
    /*
    @GetMapping("/logout")
    public String cerrarsesion(){
        return "index.html";
    }
    */
    
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate(); // Invalidar la sesión actual
        }
        return "redirect:/login?logout"; // Redirigir a la página de inicio de sesión con un mensaje de cierre de sesión
    }

    
}
