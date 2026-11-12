package com.br.projetointegrador.pi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.projetointegrador.pi.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNome(String nome);
}
