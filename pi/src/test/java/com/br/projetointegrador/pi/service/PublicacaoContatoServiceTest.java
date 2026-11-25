package com.br.projetointegrador.pi.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.br.projetointegrador.pi.dto.publicacaoContato.PublicacaoContatoRequest;
import com.br.projetointegrador.pi.dto.publicacaoContato.PublicacaoContatoResponse;
import com.br.projetointegrador.pi.enumerators.Permissao;
import com.br.projetointegrador.pi.exceptions.GenericException;
import com.br.projetointegrador.pi.model.Contato;
import com.br.projetointegrador.pi.model.Publicacao;
import com.br.projetointegrador.pi.model.PublicacaoContato;
import com.br.projetointegrador.pi.model.Usuario;
import com.br.projetointegrador.pi.model.UsuarioAutenticado;
import com.br.projetointegrador.pi.repository.ContatoRepository;
import com.br.projetointegrador.pi.repository.PublicacaoContatoRepository;
import com.br.projetointegrador.pi.repository.PublicacaoRepository;
import com.br.projetointegrador.pi.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class PublicacaoContatoServiceTest {

    @InjectMocks
    private PublicacaoContatoService publicacaoContatoService;

    @Mock
    private PublicacaoContatoRepository publicacaoContatoRepository;

    @Mock
    private PublicacaoRepository publicacaoRepository;

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private UsuarioAutenticado usuarioAutenticado;

    private MockedStatic<SecurityContextHolder> mockedSecurityContextHolder;

    @BeforeEach
    void setUp() {
        mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class);
        mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getPrincipal()).thenReturn(usuarioAutenticado);
    }

    @AfterEach
    void tearDown() {
        mockedSecurityContextHolder.close();
    }

    private Usuario criarUsuario(Long id, String email, Permissao permissao) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(id);
        usuario.setEmail(email);
        usuario.setPermissao(permissao);
        return usuario;
    }

    private Publicacao criarPublicacao(Long idPublicacao, Usuario dono) {
        Publicacao publicacao = new Publicacao();
        publicacao.setIdPublicacao(idPublicacao);
        publicacao.setIdUsuario(dono);
        return publicacao;
    }

    private Contato criarContato(Long idContato, Usuario dono, String valor) {
        Contato contato = new Contato();
        contato.setIdContato(idContato);
        contato.setIdUsuario(dono);
        contato.setContato(valor);
        return contato;
    }

    @Test
    @DisplayName("Deve listar contatos disponibilizados de uma publicação existente")
    void deveListarContatosPorPublicacaoComSucesso() {
        Usuario dono = criarUsuario(1L, "dono@email.com", Permissao.USUARIO);
        Publicacao publicacao = criarPublicacao(10L, dono);
        Contato contato = criarContato(100L, dono, "11999999999");
        PublicacaoContato associacao = new PublicacaoContato(publicacao, contato);

        when(publicacaoRepository.findById(10L)).thenReturn(Optional.of(publicacao));
        when(publicacaoContatoRepository.findByPublicacao_IdPublicacaoOrderByContato_IdContato(10L))
                .thenReturn(List.of(associacao));

        List<PublicacaoContatoResponse> resultado = publicacaoContatoService.listarPorPublicacao(10L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(publicacaoContatoRepository, times(1))
                .findByPublicacao_IdPublicacaoOrderByContato_IdContato(10L);
    }

    @Test
    @DisplayName("Deve lançar GenericException ao listar contatos de publicação inexistente")
    void deveLancarExcecaoAoListarContatosDePublicacaoInexistente() {
        when(publicacaoRepository.findById(99L)).thenReturn(Optional.empty());

        GenericException exception = assertThrows(GenericException.class,
                () -> publicacaoContatoService.listarPorPublicacao(99L));

        assertEquals("Erro! Publicação não existe!", exception.getMessage());
        assertEquals("PUBLI_NOT_FOUND", exception.getCodigoErro());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("Deve disponibilizar contato com sucesso quando usuário for o dono da publicação")
    void deveDisponibilizarContatoComSucessoComoProprietario() {
        Usuario dono = criarUsuario(1L, "dono@email.com", Permissao.USUARIO);
        Publicacao publicacao = criarPublicacao(10L, dono);
        Contato contato = criarContato(100L, dono, "11999999999");
        PublicacaoContatoRequest request = new PublicacaoContatoRequest(100L);

        when(publicacaoRepository.findById(10L)).thenReturn(Optional.of(publicacao));
        when(usuarioAutenticado.getUsername()).thenReturn("dono@email.com");
        when(usuarioRepository.findByEmail("dono@email.com")).thenReturn(dono);
        when(contatoRepository.findByIdContatoInAndIdUsuario(List.of(100L), dono))
                .thenReturn(List.of(contato));
        when(publicacaoContatoRepository.findByPublicacao_IdPublicacaoAndContato_IdContato(10L, 100L))
                .thenReturn(Optional.empty());

        PublicacaoContatoResponse response = publicacaoContatoService.disponibilizarContato(10L, request);

        assertNotNull(response);
        verify(publicacaoContatoRepository, times(1)).save(any(PublicacaoContato.class));
    }

    @Test
    @DisplayName("Deve permitir que ADMIN disponibilize contato na publicação de outro usuário")
    void devePermitirAdminDisponibilizarContato() {
        Usuario dono = criarUsuario(1L, "dono@email.com", Permissao.USUARIO);
        Usuario admin = criarUsuario(2L, "admin@email.com", Permissao.ADMIN);
        Publicacao publicacao = criarPublicacao(10L, dono);
        Contato contatoDono = criarContato(100L, dono, "11999999999");
        PublicacaoContatoRequest request = new PublicacaoContatoRequest(100L);

        when(publicacaoRepository.findById(10L)).thenReturn(Optional.of(publicacao));
        when(usuarioAutenticado.getUsername()).thenReturn("admin@email.com");
        when(usuarioRepository.findByEmail("admin@email.com")).thenReturn(admin);
        when(contatoRepository.findByIdContatoInAndIdUsuario(List.of(100L), dono))
                .thenReturn(List.of(contatoDono));
        when(publicacaoContatoRepository.findByPublicacao_IdPublicacaoAndContato_IdContato(10L, 100L))
                .thenReturn(Optional.empty());

        PublicacaoContatoResponse response = publicacaoContatoService.disponibilizarContato(10L, request);

        assertNotNull(response);
        verify(publicacaoContatoRepository, times(1)).save(any(PublicacaoContato.class));
    }

    @Test
    @DisplayName("Deve lançar ACESSO_NEGADO quando usuário não for dono nem ADMIN ao disponibilizar contato")
    void deveLancarExcecaoSemPermissaoAoDisponibilizarContato() {
        Usuario dono = criarUsuario(1L, "dono@email.com", Permissao.USUARIO);
        Usuario invasor = criarUsuario(2L, "invasor@email.com", Permissao.USUARIO);
        Publicacao publicacao = criarPublicacao(10L, dono);
        PublicacaoContatoRequest request = new PublicacaoContatoRequest(100L);

        when(publicacaoRepository.findById(10L)).thenReturn(Optional.of(publicacao));
        when(usuarioAutenticado.getUsername()).thenReturn("invasor@email.com");
        when(usuarioRepository.findByEmail("invasor@email.com")).thenReturn(invasor);

        GenericException exception = assertThrows(GenericException.class,
                () -> publicacaoContatoService.disponibilizarContato(10L, request));

        assertEquals("ACESSO_NEGADO", exception.getCodigoErro());
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
        verify(publicacaoContatoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar CONTATO_INVALIDO quando contato não pertencer ao dono da publicação")
    void deveLancarExcecaoQuandoContatoNaoPertencerAoDono() {
        Usuario dono = criarUsuario(1L, "dono@email.com", Permissao.USUARIO);
        Publicacao publicacao = criarPublicacao(10L, dono);
        PublicacaoContatoRequest request = new PublicacaoContatoRequest(999L);

        when(publicacaoRepository.findById(10L)).thenReturn(Optional.of(publicacao));
        when(usuarioAutenticado.getUsername()).thenReturn("dono@email.com");
        when(usuarioRepository.findByEmail("dono@email.com")).thenReturn(dono);
        when(contatoRepository.findByIdContatoInAndIdUsuario(List.of(999L), dono))
                .thenReturn(List.of());

        GenericException exception = assertThrows(GenericException.class,
                () -> publicacaoContatoService.disponibilizarContato(10L, request));

        assertEquals("CONTATO_INVALIDO", exception.getCodigoErro());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(publicacaoContatoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar CONTATO_JA_DISPONIBILIZADO quando contato já estiver vinculado à publicação")
    void deveLancarExcecaoQuandoContatoJaEstiverDisponibilizado() {
        Usuario dono = criarUsuario(1L, "dono@email.com", Permissao.USUARIO);
        Publicacao publicacao = criarPublicacao(10L, dono);
        Contato contato = criarContato(100L, dono, "11999999999");
        PublicacaoContato associacaoExistente = new PublicacaoContato(publicacao, contato);
        PublicacaoContatoRequest request = new PublicacaoContatoRequest(100L);

        when(publicacaoRepository.findById(10L)).thenReturn(Optional.of(publicacao));
        when(usuarioAutenticado.getUsername()).thenReturn("dono@email.com");
        when(usuarioRepository.findByEmail("dono@email.com")).thenReturn(dono);
        when(contatoRepository.findByIdContatoInAndIdUsuario(List.of(100L), dono))
                .thenReturn(List.of(contato));
        when(publicacaoContatoRepository.findByPublicacao_IdPublicacaoAndContato_IdContato(10L, 100L))
                .thenReturn(Optional.of(associacaoExistente));

        GenericException exception = assertThrows(GenericException.class,
                () -> publicacaoContatoService.disponibilizarContato(10L, request));

        assertEquals("CONTATO_JA_DISPONIBILIZADO", exception.getCodigoErro());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(publicacaoContatoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve remover contato com sucesso quando houver mais de um contato na publicação")
    void deveRemoverContatoComSucesso() {
        Usuario dono = criarUsuario(1L, "dono@email.com", Permissao.USUARIO);
        Publicacao publicacao = criarPublicacao(10L, dono);
        Contato contato = criarContato(100L, dono, "11999999999");
        PublicacaoContato associacao = new PublicacaoContato(publicacao, contato);

        when(publicacaoRepository.findById(10L)).thenReturn(Optional.of(publicacao));
        when(usuarioAutenticado.getUsername()).thenReturn("dono@email.com");
        when(usuarioRepository.findByEmail("dono@email.com")).thenReturn(dono);
        when(publicacaoContatoRepository.findByPublicacao_IdPublicacaoAndContato_IdContato(10L, 100L))
                .thenReturn(Optional.of(associacao));
        when(publicacaoContatoRepository.countByPublicacao_IdPublicacao(10L)).thenReturn(2L);

        publicacaoContatoService.removerContato(10L, 100L);

        verify(publicacaoContatoRepository, times(1)).delete(associacao);
    }

    @Test
    @DisplayName("Deve lançar PUBLICACAO_CONTATO_NOT_FOUND ao tentar remover contato não vinculado")
    void deveLancarExcecaoAoRemoverContatoNaoDisponibilizado() {
        Usuario dono = criarUsuario(1L, "dono@email.com", Permissao.USUARIO);
        Publicacao publicacao = criarPublicacao(10L, dono);

        when(publicacaoRepository.findById(10L)).thenReturn(Optional.of(publicacao));
        when(usuarioAutenticado.getUsername()).thenReturn("dono@email.com");
        when(usuarioRepository.findByEmail("dono@email.com")).thenReturn(dono);
        when(publicacaoContatoRepository.findByPublicacao_IdPublicacaoAndContato_IdContato(10L, 100L))
                .thenReturn(Optional.empty());

        GenericException exception = assertThrows(GenericException.class,
                () -> publicacaoContatoService.removerContato(10L, 100L));

        assertEquals("PUBLICACAO_CONTATO_NOT_FOUND", exception.getCodigoErro());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(publicacaoContatoRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve lançar CONTATO_OBRIGATORIO ao tentar remover o único contato da publicação")
    void deveLancarExcecaoAoRemoverUnicoContatoDaPublicacao() {
        Usuario dono = criarUsuario(1L, "dono@email.com", Permissao.USUARIO);
        Publicacao publicacao = criarPublicacao(10L, dono);
        Contato contato = criarContato(100L, dono, "11999999999");
        PublicacaoContato associacao = new PublicacaoContato(publicacao, contato);

        when(publicacaoRepository.findById(10L)).thenReturn(Optional.of(publicacao));
        when(usuarioAutenticado.getUsername()).thenReturn("dono@email.com");
        when(usuarioRepository.findByEmail("dono@email.com")).thenReturn(dono);
        when(publicacaoContatoRepository.findByPublicacao_IdPublicacaoAndContato_IdContato(10L, 100L))
                .thenReturn(Optional.of(associacao));
        when(publicacaoContatoRepository.countByPublicacao_IdPublicacao(10L)).thenReturn(1L);

        GenericException exception = assertThrows(GenericException.class,
                () -> publicacaoContatoService.removerContato(10L, 100L));

        assertEquals("CONTATO_OBRIGATORIO", exception.getCodigoErro());
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(publicacaoContatoRepository, never()).delete(any());
    }
}