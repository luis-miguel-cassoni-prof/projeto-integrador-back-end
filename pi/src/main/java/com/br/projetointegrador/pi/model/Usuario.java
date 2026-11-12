package com.br.projetointegrador.pi.model;

import com.br.projetointegrador.pi.enumerators.Permissao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter 
@Setter 
public class Usuario {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @NotBlank (message = "O nome não pode estar vazio")
    @Column (length = 50)
    private String nome;

    @Email 
    @NotBlank (message = "O e-mail não pode estar vazio")
    private String email;

    @Column (length = 60)
    @NotBlank (message = "O campo senha não pode estar vazio")
    private String hashSenha;

    @Enumerated (EnumType.STRING)
    private Permissao permissao;
}
