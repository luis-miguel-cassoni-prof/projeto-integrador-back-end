package com.br.projetointegrador.pi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetointegrador.pi.dto.usuario.UsuarioCadastrarRequest;
import com.br.projetointegrador.pi.dto.usuario.UsuarioLoginRequest;
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

    @PostMapping ("/login")
    public UsuarioResponse login (@Valid @RequestBody UsuarioLoginRequest usuarioRequest) {
        return usuarioService.login(usuarioRequest);
    }
}
