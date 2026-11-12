package com.br.projetointegrador.pi.dto.usuario;

import com.br.projetointegrador.pi.enumerators.Permissao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UsuarioResponse(
    @NotNull (message = "O ID não pode ser nulo")
    @NotEmpty (message = "O ID não pode estar vazio")
    Long idUsuario,

    @NotBlank (message = "O nome não pode estar vazio")
    String nome,
    
    @NotBlank (message = "A permissão não pode estar vazia")
    Permissao permissao) {}
