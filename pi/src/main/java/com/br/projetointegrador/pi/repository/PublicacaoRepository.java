package com.br.projetointegrador.pi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetointegrador.pi.model.Publicacao;

public interface PublicacaoRepository extends JpaRepository <Publicacao, Long> {
    List<Publicacao> findByNomeContainingIgnoreCase(String nome);
}
