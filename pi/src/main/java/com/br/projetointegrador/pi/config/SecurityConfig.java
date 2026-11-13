package com.br.projetointegrador.pi.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import jakarta.servlet.DispatcherType;

@Configuration 
public class SecurityConfig {

    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        .cors(Customizer.withDefaults())
        .csrf(Customizer.withDefaults())
        .authorizeHttpRequests(authReq -> {
            authReq
            .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
            .requestMatchers("/usuario/autenticacao/cadastrar", "/usuario/autenticacao/login", "/usuario/autenticacao/csrf", "/logout")
            .permitAll()
            .anyRequest().authenticated();
        })
        .exceptionHandling(exception -> exception
        .authenticationEntryPoint((request, response, authException) -> {
            response.setStatus(401);
        }))
        .formLogin(form -> form
            .loginProcessingUrl("/usuario/autenticacao/login")
            .usernameParameter("email")
            .passwordParameter("senha")
            .successHandler((request, response, authentication) -> {
                response.setStatus(200);
            })
            .failureHandler((request, response, exception) -> {
                response.setStatus(401);
            })
            .permitAll()
        )
        .logout(logout -> logout
        .logoutSuccessHandler((request, response, authentication) -> {
            response.setStatus(204);
        }))
        .build();
    }

    @Bean 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
