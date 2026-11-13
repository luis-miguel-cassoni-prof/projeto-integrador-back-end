package com.br.projetointegrador.pi.dto.exception;

import java.time.Instant;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
public class ExceptionDTO {
    private String codigoErro;
    private String mensagem;
    private int status;
    private Instant timestamp;
    private String path;
}
