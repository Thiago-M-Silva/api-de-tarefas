package com.api_tarefas.api_de_tarefas.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.api_tarefas.api_de_tarefas.Controller.PessoaController;
import com.api_tarefas.api_de_tarefas.Model.Departamento.DepartamentoDTO;
import com.api_tarefas.api_de_tarefas.Model.Pessoa.Pessoa;
import com.api_tarefas.api_de_tarefas.Model.Pessoa.PessoaRepository;
import com.api_tarefas.api_de_tarefas.Model.Pessoa.PessoaRequestDTO;
import com.api_tarefas.api_de_tarefas.Model.Pessoa.PessoaResponseDTO;
import com.api_tarefas.api_de_tarefas.Model.Tarefa.Tarefa;
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
import java.util.Date;
import java.util.List;

@WebMvcTest(PessoaController.class)
public class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PessoaRepository pessoaRep;

    @Autowired
    private ObjectMapper objectMapper;

    private PessoaRequestDTO pessoaRequestDTO;
    private PessoaResponseDTO pessoaResponseDTO;
    private TarefaRequestDTO tarefaRequestDTO;
    private Tarefa tarefa;

    @BeforeEach
    public void setUp() {
        tarefaRequestDTO = new TarefaRequestDTO(1L, "Descricao", "Departamento", new Date(),
                "Desenvolvimento", 15F, null, true);
        tarefa = new Tarefa(tarefaRequestDTO);

        pessoaRequestDTO = new PessoaRequestDTO(1L, "Thiago", "Desenvolvimento", null);
        pessoaResponseDTO = new PessoaResponseDTO(1L, "Thiago", "Desenvolvimento", null);
    }

    @Test
    public void testTempMedTarefa() throws Exception {
        List<PessoaResponseDTO> pessoas = Arrays.asList(pessoaResponseDTO);
        when(pessoaRep.listPessoaHoraTarefa()).thenReturn(pessoas);

        mockMvc.perform(get("/pessoa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].campo").value(pessoaResponseDTO.getClass()));

        verify(pessoaRep, times(1)).listPessoaHoraTarefa();
    }

    @Test
    public void testListPessoa() throws Exception {
        List<PessoaResponseDTO> pessoas = Arrays.asList(pessoaResponseDTO);
        when(pessoaRep.getMediaTarefa(anyString(), anyLong(), anyLong())).thenReturn(pessoas);

        mockMvc.perform(get("/pessoa/gastos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("nome")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].campo").value(pessoaResponseDTO.getClass()));

        verify(pessoaRep, times(1)).getMediaTarefa(anyString(), anyLong(), anyLong());
    }

    @Test
    public void testAddPessoa() throws Exception {
        mockMvc.perform(post("/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaRequestDTO)))
                .andExpect(status().isOk());

        verify(pessoaRep, times(1)).save(any(Pessoa.class));
    }

    @Test
    public void testUpdatePessoa() throws Exception {
        mockMvc.perform(put("/pessoa/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaRequestDTO)))
                .andExpect(status().isOk());

        verify(pessoaRep, times(1)).deleteById(anyLong());
        verify(pessoaRep, times(1)).save(any(Pessoa.class));
    }

    @Test
    public void testRemovePessoa() throws Exception {
        mockMvc.perform(delete("/pessoa/1"))
                .andExpect(status().isOk());

        verify(pessoaRep, times(1)).deleteById(anyLong());
    }

    @Test
    public void testListarDepartamentosComQuantidade() throws Exception {
        List<DepartamentoDTO> departamentos = Arrays.asList(new DepartamentoDTO("Desenvolvimento", 2L, 3L));
        when(pessoaRep.listarDepartamentos()).thenReturn(departamentos);

        mockMvc.perform(get("/pessoa/departamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].campo").value(departamentos.get(0).getClass()));

        verify(pessoaRep, times(1)).listarDepartamentos();
    }
}
