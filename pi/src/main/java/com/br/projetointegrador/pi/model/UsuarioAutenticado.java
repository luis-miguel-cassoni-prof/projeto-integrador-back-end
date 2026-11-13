package com.br.projetointegrador.pi.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioAutenticado implements UserDetails {
    private Usuario usuario;

    public UsuarioAutenticado(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override 
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getPermissao().name()));
    }

    @Override 
    public String getPassword() {
        return usuario.getHashSenha();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }
}
