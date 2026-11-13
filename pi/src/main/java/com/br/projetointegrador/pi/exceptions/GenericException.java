package com.br.projetointegrador.pi.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode (callSuper = true)
@NoArgsConstructor (force = true)
@Data 
public class GenericException extends RuntimeException {
    private final String codigoErro;
    private final HttpStatus status;

    public GenericException(String mensagem, String codigoErro, HttpStatus status) {
        super(mensagem);
        this.codigoErro = codigoErro;
        this.status = status;
    }
}
