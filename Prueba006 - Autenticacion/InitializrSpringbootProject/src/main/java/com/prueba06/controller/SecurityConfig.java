package com.prueba06.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    // DAR AUTORIZACION A LAS RUTAS
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        authorize -> authorize
                                // AUTORIZAR A CUALQUIERA QUE NO SE HAYA REGISTRADO
                                // .requestMatchers("/", "/login")
                                // .permitAll()
                                // AUTORIZAR A CUALQUIERA QUE SE HAYA REGISTRADO (USUARIOS Y ADMINISTRADORES)
                                // EL USUARIO PODRA VER LOS PRODUCTOS
                                .requestMatchers("/productos")
                                .hasAnyRole("USER", "ADMIN")
                                // AUTORIZAR A SOLAMENTE A ADMINISTRADORES
                                // EL ADMINISTRADOR PODRA MODIFICAR LOS PRODUCTOS
                                .requestMatchers(
                                        "/productos/nuevo",
                                        "/productos/editar/**",
                                        "/productos/cambiarestadofalse/**",
                                        "/productos/eliminardefinitivamente/**"
                                )
                                // O SIMPLEMENTE USAR: .requestMatchers("/productos/**")
                                .hasRole("ADMIN")
                                // TODAS LAS DEMAS PAGINAS NO VAN A REQUERIR DE UN LOGIN...
                                .anyRequest().permitAll()
                )
                // HACIA EL FORMULARIO DE LOGIN
                .formLogin(
                        form -> form.loginPage("/login")
                                // .loginProcessingUrl("/login?process")
                                // .failureUrl("/login?error")
                                .loginProcessingUrl("/login")
                                .failureUrl("/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                // IR A LA RUTA... SI SE HA REGISTRADO CORRECTAMENTE
                                .defaultSuccessUrl("/")
                )
                // CERRAR SESION
                .logout(
                        // INVALIDAR LA SESION CUANDO CIERRE SESION AL IR A LA RUTA ...
                        logout -> logout.logoutSuccessUrl("/login?logout").permitAll().invalidateHttpSession(true)
                );

        return http.build();
    }

}
