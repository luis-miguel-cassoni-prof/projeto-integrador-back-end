package com.br.projetointegrador.pi.dto.publicacao;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PublicacaoResponse(
    @NotNull (message = "idPublicacao não pode ser nulo!")
    @NotEmpty (message = "idPublicacao não pode ser vazio!")
    Long idPublicacao,

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

    @NotNull (message = "dataCriacao não pode ser nulo!")
    @NotEmpty (message = "dataCriacao não pode ser vazio!")
    LocalDateTime dataCriacao,

    @NotNull (message = "dataOcorrencia não pode ser nulo!")
    @NotEmpty (message = "dataOcorrencia não pode ser vazio!")
    Date dataOcorrencia,

    @NotBlank (message = "Status não pode ser vazio")
    String status,
    
    @NotBlank (message = "Nome não pode ser vazio")
    String tipo
) {}
