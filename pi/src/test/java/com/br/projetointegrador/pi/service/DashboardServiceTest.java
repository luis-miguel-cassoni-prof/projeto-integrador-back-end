package com.br.projetointegrador.pi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.projetointegrador.pi.dto.dashboard.DashboardResponse;
import com.br.projetointegrador.pi.enumerators.StatusPublicacao;
import com.br.projetointegrador.pi.enumerators.TipoPublicacao;
import com.br.projetointegrador.pi.repository.PublicacaoRepository;
import com.br.projetointegrador.pi.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @InjectMocks
    private DashboardService dashboardService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PublicacaoRepository publicacaoRepository;

    @Test
    @DisplayName("Deve retornar as métricas do dashboard com sucesso")
    void deveRetornarMetricasComSucesso() {
        when(usuarioRepository.count()).thenReturn(15L);
        when(publicacaoRepository.countByStatus(StatusPublicacao.ATIVA)).thenReturn(8L);
        when(publicacaoRepository.countByStatus(StatusPublicacao.FINALIZADA)).thenReturn(4L);
        when(publicacaoRepository.countByTipo(TipoPublicacao.PERDIDO)).thenReturn(7L);
        when(publicacaoRepository.countByTipo(TipoPublicacao.ENCONTRADO)).thenReturn(5L);

        DashboardResponse response = dashboardService.retornarMetricas();

        assertNotNull(response);
        assertEquals(15L, response.usuariosCadastrados());
        assertEquals(8L, response.publicacoesAtivas());
        assertEquals(4L, response.publicacoesFinalizadas());
        assertEquals(7L, response.perdidos());
        assertEquals(5L, response.encontrados());

        verify(usuarioRepository, times(1)).count();
        verify(publicacaoRepository, times(1)).countByStatus(StatusPublicacao.ATIVA);
        verify(publicacaoRepository, times(1)).countByStatus(StatusPublicacao.FINALIZADA);
        verify(publicacaoRepository, times(1)).countByTipo(TipoPublicacao.PERDIDO);
        verify(publicacaoRepository, times(1)).countByTipo(TipoPublicacao.ENCONTRADO);
    }

    @Test
    @DisplayName("Deve retornar métricas zeradas quando não houver registros cadastrados")
    void deveRetornarMetricasZeradasQuandoNaoHouverRegistros() {
        when(usuarioRepository.count()).thenReturn(0L);
        when(publicacaoRepository.countByStatus(StatusPublicacao.ATIVA)).thenReturn(0L);
        when(publicacaoRepository.countByStatus(StatusPublicacao.FINALIZADA)).thenReturn(0L);
        when(publicacaoRepository.countByTipo(TipoPublicacao.PERDIDO)).thenReturn(0L);
        when(publicacaoRepository.countByTipo(TipoPublicacao.ENCONTRADO)).thenReturn(0L);

        DashboardResponse response = dashboardService.retornarMetricas();

        assertNotNull(response);
        assertEquals(0L, response.usuariosCadastrados());
        assertEquals(0L, response.publicacoesAtivas());
        assertEquals(0L, response.publicacoesFinalizadas());
        assertEquals(0L, response.perdidos());
        assertEquals(0L, response.encontrados());
    }
}