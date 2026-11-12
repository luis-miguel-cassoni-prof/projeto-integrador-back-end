package com.br.projetointegrador.pi.dto.publicacao;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PublicacaoRequest(
    @NotNull (message = "idUsuario não pode ser nulo!")
    @NotEmpty (message = "idUsuario não pode ser vazio!")
    Long idUsuario,

    @NotNull (message = "idLocal não pode ser nulo!")
    @NotEmpty (message = "idLocal não pode ser vazio!")
    Short idLocal,

    @NotBlank (message = "Nome não pode ser vazio")
    String nome,

    @NotBlank (message = "Descrição não pode ser vazio")
    String descricao,

    Date dataOcorrencia,
    @NotBlank (message = "Status não pode ser vazio")
    String status,
    
    @NotBlank (message = "Nome não pode ser vazio")
    String tipo
) {}
