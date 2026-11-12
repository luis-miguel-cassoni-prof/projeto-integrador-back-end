package com.br.projetointegrador.pi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Getter 
@Setter 
public class Local {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Short idLocal;

    @NotBlank (message = "O campo nome não pode estar vazio!")
    @Column (length = 50)
    private String nome;
}
