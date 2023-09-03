package com.prueba06.controller;

import com.prueba06.dto.UsuarioDTO;
import com.prueba06.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registro")
public class RegistroUsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @ModelAttribute("usuario")
    public UsuarioDTO retornarNuevoUsuarioDTO() {
        return new UsuarioDTO();
    }
    
    @GetMapping
    public String mostrarFormularioDeRegistro(){
        return "registro.html";
    }
    
    @PostMapping
    public String registrarCuentaDeUsuario(@ModelAttribute ("usuario") UsuarioDTO usuarioDTO){
        usuarioService.GuardarUsuario(usuarioDTO);
        // PARAMETRO "exito" EN LA URL AL REGISTRAR UNA CUENTA DE USUARIO
        return "redirect:/registro?exito";
    }
    
}
