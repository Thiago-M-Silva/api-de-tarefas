package com.api_tarefas.api_de_tarefas.Model.Tarefa;

import com.api_tarefas.api_de_tarefas.Model.Pessoa.Pessoa;

import java.util.List;

public record TarefaRequestDTO(Long id, String titulo, String descricao, Long prazo, String Departamento,
                               Float duracao, Pessoa pessoa, String status){

}
