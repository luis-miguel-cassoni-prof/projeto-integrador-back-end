package com.br.projetointegrador.pi.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.br.projetointegrador.pi.dto.local.LocalResponse;
import com.br.projetointegrador.pi.exceptions.GenericException;
import com.br.projetointegrador.pi.model.Local;
import com.br.projetointegrador.pi.repository.LocalRepository;

@ExtendWith(MockitoExtension.class)
class LocalServiceTest {

    @InjectMocks
    private LocalService localService;

    @Mock
    private LocalRepository localRepository;

    @Test
    @DisplayName("Deve listar todos os locais cadastrados com sucesso")
    void deveListarTodosOsLocaisComSucesso() {
        Local local1 = new Local();
        local1.setIdLocal((short) 1);
        local1.setNome("Laboratório 01");

        Local local2 = new Local();
        local2.setIdLocal((short) 2);
        local2.setNome("Biblioteca");

        when(localRepository.findAll()).thenReturn(List.of(local1, local2));

        List<LocalResponse> resultado = localService.listarTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals((short) 1, resultado.get(0).idLocal());
        assertEquals("Laboratório 01", resultado.get(0).nome());
        assertEquals((short) 2, resultado.get(1).idLocal());
        assertEquals("Biblioteca", resultado.get(1).nome());
        verify(localRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não houver locais cadastrados")
    void deveRetornarListaVaziaQuandoNaoHouverLocais() {
        when(localRepository.findAll()).thenReturn(List.of());

        List<LocalResponse> resultado = localService.listarTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(localRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve buscar local por ID com sucesso quando existir")
    void deveBuscarLocalPorIdComSucesso() {
        Local local = new Local();
        local.setIdLocal((short) 1);
        local.setNome("Auditório");

        when(localRepository.findById((short) 1)).thenReturn(Optional.of(local));

        LocalResponse resultado = localService.buscarPorId((short) 1);

        assertNotNull(resultado);
        assertEquals((short) 1, resultado.idLocal());
        assertEquals("Auditório", resultado.nome());
        verify(localRepository, times(1)).findById((short) 1);
    }

    @Test
    @DisplayName("Deve lançar GenericException quando o local não for encontrado pelo ID")
    void deveLancarExcecaoQuandoLocalNaoForEncontrado() {
        when(localRepository.findById((short) 99)).thenReturn(Optional.empty());

        GenericException exception = assertThrows(
                GenericException.class,
                () -> localService.buscarPorId((short) 99)
        );

        assertEquals("Erro! Local não encontrado", exception.getMessage());
        assertEquals("LOCAL_NOT_FOUND", exception.getCodigoErro());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(localRepository, times(1)).findById((short) 99);
    }
}