package com.br.projetointegrador.pi.dto.usuario;

import com.br.projetointegrador.pi.enumerators.Permissao;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRequest(
    @NotBlank (message = "O nome não pode estar vazio")
    String nome,
    
    @NotBlank (message = "A permissão não pode estar vazia")
    Permissao permissao
) {}
