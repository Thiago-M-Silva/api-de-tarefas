package com.api_tarefas.api_de_tarefas.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.api_tarefas.api_de_tarefas.Controller.TarefaController;
import com.api_tarefas.api_de_tarefas.Model.Pessoa.Pessoa;
import com.api_tarefas.api_de_tarefas.Model.Pessoa.PessoaRepository;
import com.api_tarefas.api_de_tarefas.Model.Tarefa.Tarefa;
import com.api_tarefas.api_de_tarefas.Model.Tarefa.TarefaRepository;
import com.api_tarefas.api_de_tarefas.Model.Tarefa.TarefaRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@WebMvcTest(TarefaController.class)
public class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TarefaRepository tarefaRep;

    @MockBean
    private PessoaRepository pessoaRep;

    @Autowired
    private ObjectMapper objectMapper;

    private Tarefa tarefa;
    private TarefaRequestDTO tarefaRequestDTO;
    private Pessoa pessoa;

    @BeforeEach
    public void setUp() {
        tarefaRequestDTO = new TarefaRequestDTO(1L, "Bug", "Bug de cadastro", new Date(),
                "Desenvolvimento", 25F, null, true);
        tarefa = new Tarefa(tarefaRequestDTO);
        pessoa = new Pessoa();
    }

    @Test
    public void testListaTarefaVazia() throws Exception {
        List<Tarefa> tarefas = Arrays.asList(tarefa);
        when(tarefaRep.get3Pendentes()).thenReturn(tarefas);

        mockMvc.perform(get("/tarefas/pendentes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value(tarefa.getTitulo()));

        verify(tarefaRep, times(1)).get3Pendentes();
    }

    @Test
    public void testAddTarefa() throws Exception {
        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tarefaRequestDTO)))
                .andExpect(status().isOk());

        verify(tarefaRep, times(1)).save(any(Tarefa.class));
    }

    @Test
    public void testFinalizaTarefa() throws Exception {
        Tarefa tarefaToFinalize = new Tarefa();
        tarefaToFinalize.setStatus(false);

        when(tarefaRep.save(any(Tarefa.class))).thenReturn(tarefaToFinalize);

        mockMvc.perform(put("/tarefas/finalizar/1"))
                .andExpect(status().isOk());

        verify(tarefaRep, times(1)).save(any(Tarefa.class));
    }

    @Test
    public void testSetTarefaPessoa() throws Exception {
        when(tarefaRep.findById(anyLong())).thenReturn(Optional.of(tarefa));
        when(pessoaRep.findByDepartamento(anyString())).thenReturn(Arrays.asList(pessoa));

        mockMvc.perform(put("/tarefas/alocar/1"))
                .andExpect(status().isOk());

        verify(tarefaRep, times(1)).findById(anyLong());
        verify(pessoaRep, times(1)).findByDepartamento(anyString());
        verify(tarefaRep, times(1)).save(any(Tarefa.class));
    }

    @Test
    public void testSetTarefaPessoa_TarefaNaoEncontrada() throws Exception {
        when(tarefaRep.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put("/tarefas/alocar/1"))
                .andExpect(status().isInternalServerError());

        verify(tarefaRep, times(1)).findById(anyLong());
        verify(pessoaRep, times(0)).findByDepartamento(anyString());
        verify(tarefaRep, times(0)).save(any(Tarefa.class));
    }

    @Test
    public void testSetTarefaPessoa_TarefaJaAlocada() throws Exception {
        tarefa.setPessoa(pessoa);  // Simula a tarefa já alocada

        when(tarefaRep.findById(anyLong())).thenReturn(Optional.of(tarefa));

        mockMvc.perform(put("/tarefas/alocar/1"))
                .andExpect(status().isInternalServerError());

        verify(tarefaRep, times(1)).findById(anyLong());
        verify(pessoaRep, times(0)).findByDepartamento(anyString());
        verify(tarefaRep, times(0)).save(any(Tarefa.class));
    }

    @Test
    public void testSetTarefaPessoa_NenhumaPessoaDisponivel() throws Exception {
        when(tarefaRep.findById(anyLong())).thenReturn(Optional.of(tarefa));
        when(pessoaRep.findByDepartamento(anyString())).thenReturn(Arrays.asList());

        mockMvc.perform(put("/tarefas/alocar/1"))
                .andExpect(status().isInternalServerError());

        verify(tarefaRep, times(1)).findById(anyLong());
        verify(pessoaRep, times(1)).findByDepartamento(anyString());
        verify(tarefaRep, times(0)).save(any(Tarefa.class));
    }
}
