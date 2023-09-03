package com.prueba06.service;

import com.prueba06.dto.UsuarioDTO;
import com.prueba06.entity.UsuarioEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioService extends UserDetailsService{
    
    public UsuarioEntity GuardarUsuario(UsuarioDTO usuarioDTO);

    public UsuarioEntity obtenerUsuarioPorEmail(String username);

    
}
