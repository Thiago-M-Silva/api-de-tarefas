package com.api_tarefas.api_de_tarefas.Model.Tarefa;

import com.api_tarefas.api_de_tarefas.Model.Pessoa.Pessoa;

import java.util.Date;
import java.util.List;

public record TarefaResponseDTO(Long id, String titulo, String descricao, Date prazo, String Departamento,
                                Float duracao, Pessoa pessoa, Boolean status) {

    public TarefaResponseDTO(Tarefa tarefa){
        this(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getPrazo(),
                tarefa.getDepartamento(),
                tarefa.getDuracao(),
                tarefa.getPessoa(),
                tarefa.getStatus()
        );
    }
}
