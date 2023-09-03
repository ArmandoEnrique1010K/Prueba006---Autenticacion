package com.prueba06.controller;

import com.prueba06.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.prueba06.controller.PasswordEncoderProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioService usuarioService;

    // Encriptar contraseña
    @Autowired
    private PasswordEncoderProvider passwordEncoder;

    // Autenticar a los usuarios consultando una fuente de datos
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(usuarioService);
        auth.setPasswordEncoder(passwordEncoder.passwordEncoder());
        return auth;

    }

    // Se encarga de manejar y autenticar las solicitudes de inicio de sesión de los usuarios.
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    /*
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
     */
    // DAR AUTORIZACION A LAS RUTAS
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        authorize -> authorize
                                // AUTORIZAR A CUALQUIERA QUE NO SE HAYA REGISTRADO
                                // INCLUIR LOS ARCHIVOS CSS, JS Y LAS IMAGENES ESTATICAS
                                // .requestMatchers("/", "/login", "/js/**", "/css/**", "/img/**")
                                // .permitAll()

                                // AUTORIZAR A CUALQUIERA QUE SE HAYA REGISTRADO (USUARIOS Y ADMINISTRADORES)
                                .requestMatchers("/productos")
                                .hasAnyAuthority("USER", "ADMIN")
                                
                                // AUTORIZAR A SOLAMENTE A ADMINISTRADORES
                                .requestMatchers(
                                        "/productos/nuevo",
                                        "/productos/editar/**",
                                        "/productos/cambiarestadofalse/**",
                                        "/productos/eliminardefinitivamente/**"
                                )
                                // O SIMPLEMENTE USAR: .requestMatchers("/productos/**")
                                .hasAuthority("ADMIN")
                                // TODAS LAS DEMAS PAGINAS NO VAN A REQUERIR DE UN LOGIN
                                .anyRequest().permitAll()
                        
                // PERO: SI TODAS LAS DEMAS PAGINAS VAN A REQUERIR DE UN LOGIN...
                // (EL USUARIO DEBE ESTAR REGISTRADO PARA QUE PUEDA ACCEDER A LA PAGINA)...
                // .anyRequest().authenticated()
                )
                
                // HACIA EL FORMULARIO DE LOGIN
                .formLogin(
                        form -> form.loginPage("/login")
                                .failureUrl("/login?error")
                                //.loginProcessingUrl("/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                // IR A LA RUTA... SI SE HA REGISTRADO CORRECTAMENTE
                                .defaultSuccessUrl("/")
                )
                
                // CERRAR SESION
                .logout(
                        // INVALIDAR LA SESION CUANDO CIERRE SESION AL IR A LA RUTA ...
                        logout -> logout
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login?logout").permitAll()
                );

        return http.build();
    }
}
