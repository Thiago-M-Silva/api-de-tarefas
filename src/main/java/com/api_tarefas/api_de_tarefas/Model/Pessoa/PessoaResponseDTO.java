package com.api_tarefas.api_de_tarefas.Model.Pessoa;

import com.api_tarefas.api_de_tarefas.Model.Tarefa.Tarefa;

import java.util.List;

public record PessoaResponseDTO(Long id, String nome, String departamento, List<Tarefa> tarefa) {

    public PessoaResponseDTO(Pessoa pessoa){
        this(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getDepartamento(),
                pessoa.getTarefas()
        );
    }
}
