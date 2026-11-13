package com.br.projetointegrador.pi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.projetointegrador.pi.dto.usuario.UsuarioCadastrarRequest;
import com.br.projetointegrador.pi.dto.usuario.UsuarioResponse;
import com.br.projetointegrador.pi.enumerators.Permissao;
import com.br.projetointegrador.pi.exceptions.GenericException;
import com.br.projetointegrador.pi.model.Usuario;
import com.br.projetointegrador.pi.model.UsuarioAutenticado;
import com.br.projetointegrador.pi.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Service 
@Slf4j 
public class UsuarioService implements UserDetailsService {

    @Autowired 
    private UsuarioRepository usuarioRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    public UsuarioResponse cadastro(UsuarioCadastrarRequest usuarioRequest) {
        Usuario usuario = new Usuario();

        if(!usuarioRequest.email().endsWith("@unifio.edu.br")) {
            throw new GenericException("Erro! O e-mail deve ser o institucional da unifio", "WRONG_EMAIL", HttpStatus.BAD_REQUEST);
        }

        if(usuarioRepository.existsByEmail(usuarioRequest.email())) {
            throw new GenericException("Erro! O e-mail já está sendo utilizado!", "EMAIL_ALREADY_EXISTS", HttpStatus.CONFLICT);
        }

        usuario.setNome(usuarioRequest.nome());
        usuario.setEmail(usuarioRequest.email());
        usuario.setHashSenha(passwordEncoder.encode(usuarioRequest.senha()));
        usuario.setPermissao(Permissao.USUARIO);

        log.info("Cadastrando usuário!");

        usuarioRepository.save(usuario);

        return new UsuarioResponse(usuario.getIdUsuario(), usuario.getNome(), usuario.getPermissao());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new UsernameNotFoundException("O email ou a senha estão incorretos");
        }

        return new UsuarioAutenticado(usuario);
    }
}