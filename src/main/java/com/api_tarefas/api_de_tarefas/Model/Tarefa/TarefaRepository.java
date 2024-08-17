package com.api_tarefas.api_de_tarefas.Model.Tarefa;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    @Query(
            "SELECT t" +
            "FROM tarefa t" +
            "WHERE t.pessoa IS NULL" +
            "ORDER BY t.prazo ASC"
    )
    List<Tarefa> getPendentes(Pageable pageable);
    default List<Tarefa> get3Pendentes(){
        return getPendentes(PageRequest.of(0, 3));
    }
}
