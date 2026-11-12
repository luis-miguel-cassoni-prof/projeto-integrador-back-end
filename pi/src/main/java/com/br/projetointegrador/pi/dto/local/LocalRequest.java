package com.br.projetointegrador.pi.dto.local;

import jakarta.validation.constraints.NotBlank;

public record LocalRequest(
    @NotBlank (message = "O nome não pode estar vazio")
    String nome
) {}
