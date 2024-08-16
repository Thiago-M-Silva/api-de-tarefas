package com.api_tarefas.api_de_tarefas.Model.Tarefa;

import com.api_tarefas.api_de_tarefas.Model.Pessoa.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Pessoa, Long> {

}
