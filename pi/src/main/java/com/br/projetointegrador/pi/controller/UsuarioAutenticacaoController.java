package com.br.projetointegrador.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetointegrador.pi.dto.usuario.UsuarioCadastrarRequest;
import com.br.projetointegrador.pi.dto.usuario.UsuarioResponse;
import com.br.projetointegrador.pi.service.UsuarioService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/usuario/autenticacao")
public class UsuarioAutenticacaoController {
    
    @Autowired 
    private UsuarioService usuarioService;

    @PostMapping ("/cadastrar")
    public UsuarioResponse cadastrar(@Valid @RequestBody UsuarioCadastrarRequest usuarioRequest) {
        return usuarioService.cadastro(usuarioRequest);
    }

    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken csrfToken) {
        return csrfToken;
    }
}
