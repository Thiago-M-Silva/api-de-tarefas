package com.api_tarefas.api_de_tarefas.Model.Pessoa;

import com.api_tarefas.api_de_tarefas.Model.Tarefa.Tarefa;

import java.util.List;

public record PessoaRequestDTO(Long id, String nome, String departamento, List<Tarefa> tarefas){

}
