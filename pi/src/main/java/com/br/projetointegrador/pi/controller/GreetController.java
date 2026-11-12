package com.br.projetointegrador.pi.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetointegrador.pi.model.UsuarioAutenticado;

@RestController 
@RequestMapping ("/greet")
public class GreetController {
    
    @GetMapping 
    public String greet() {
        UsuarioAutenticado usuario = (UsuarioAutenticado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Oi " + usuario.getUsername();
    }
}
