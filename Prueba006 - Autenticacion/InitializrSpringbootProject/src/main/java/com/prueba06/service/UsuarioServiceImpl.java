package com.prueba06.service;

import com.prueba06.dto.UsuarioDTO;
import com.prueba06.entity.RolEntity;
import com.prueba06.entity.UsuarioEntity;
import com.prueba06.repository.UsuarioRepository;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public UsuarioEntity GuardarUsuario(UsuarioDTO usuarioDTO) {
        
        UsuarioEntity usuarioEntity = new UsuarioEntity(
                usuarioDTO.getNombre(),
                usuarioDTO.getApellido(),
                usuarioDTO.getEmail(),
                // contraseña encriptada
                passwordEncoder.encode(usuarioDTO.getPassword()),
                Arrays.asList(new RolEntity("USER"))
        );
    
        return usuarioRepository.save(usuarioEntity);
    }

    // BUSCAR USUARIO POR SU EMAIL
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuarioEntity = usuarioRepository.findUserByEmail(username);
        if (usuarioEntity == null){
            throw new UsernameNotFoundException("Usuario o contraseña invalido(a)");
        }
        return new User(usuarioEntity.getEmail(), usuarioEntity.getPassword(), mapearAutoridadesARoles(usuarioEntity.getRoles()));
    }
    
    // MAPEAR AUTORIDADES A ROLES
    private Collection <? extends GrantedAuthority> mapearAutoridadesARoles(Collection<RolEntity> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
        
    }

    @Override
    public UsuarioEntity obtenerUsuarioPorEmail(String username) {
        return usuarioRepository.findUserByEmail(username);
    }
    
}




















