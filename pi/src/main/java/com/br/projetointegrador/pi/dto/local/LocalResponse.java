package com.br.projetointegrador.pi.dto.local;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LocalResponse(
    @NotNull (message = "O idLocal não pode ser nulo")
    @NotEmpty (message = "O idLocal não pode estar vazio")
    Short idLocal,
    @NotBlank (message = "O nome não pode estar vazio")
    String nome
) {}
