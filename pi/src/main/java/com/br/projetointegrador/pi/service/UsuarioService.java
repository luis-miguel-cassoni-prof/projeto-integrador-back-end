package com.br.projetointegrador.pi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.projetointegrador.pi.dto.usuario.UsuarioCadastrarRequest;
import com.br.projetointegrador.pi.dto.usuario.UsuarioResponse;
import com.br.projetointegrador.pi.enumerators.Permissao;
import com.br.projetointegrador.pi.model.Usuario;
import com.br.projetointegrador.pi.model.UsuarioAutenticado;
import com.br.projetointegrador.pi.repository.UsuarioRepository;

@Service 
public class UsuarioService implements UserDetailsService {

    @Autowired 
    private UsuarioRepository usuarioRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;    

    public UsuarioResponse cadastro(UsuarioCadastrarRequest usuarioRequest) {
        Usuario usuario = new Usuario();

        usuario.setNome(usuarioRequest.nome());
        usuario.setEmail(usuarioRequest.email());
        usuario.setHashSenha(passwordEncoder.encode(usuarioRequest.senha()));
        usuario.setPermissao(Permissao.USUARIO);

        usuarioRepository.save(usuario);

        return new UsuarioResponse(usuario.getIdUsuario(), usuario.getNome(), usuario.getPermissao());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByNome(username);
        return new UsuarioAutenticado(u);
    }
}
