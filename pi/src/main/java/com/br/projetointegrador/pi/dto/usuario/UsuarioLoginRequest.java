package com.br.projetointegrador.pi.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginRequest(
    @NotBlank (message = "O nome não pode estar vazio")
    String nome,

    @Email 
    @NotBlank (message = "O e-mail não pode estar vazio")
    String email,
    
    @NotBlank (message = "A senha não pode estar vazia")
    String senha
) {}
