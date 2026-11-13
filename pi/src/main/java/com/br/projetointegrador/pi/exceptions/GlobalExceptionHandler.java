package com.br.projetointegrador.pi.exceptions;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.br.projetointegrador.pi.dto.exception.ExceptionDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice 
public class GlobalExceptionHandler {
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ExceptionDTO> handleGenericException(GenericException ex, HttpServletRequest request) {
        ExceptionDTO error = new ExceptionDTO();
        error.setCodigoErro(ex.getCodigoErro());
        error.setMensagem(ex.getMessage());
        error.setStatus(ex.getStatus().value());
        error.setTimestamp(Instant.now());
        error.setPath(request.getRequestURI());
        return new ResponseEntity<>(error, ex.getStatus());
    }
}
