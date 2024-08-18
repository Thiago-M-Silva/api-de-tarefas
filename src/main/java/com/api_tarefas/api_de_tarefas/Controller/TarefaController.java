package com.api_tarefas.api_de_tarefas.Controller;

import com.api_tarefas.api_de_tarefas.Model.Pessoa.Pessoa;
import com.api_tarefas.api_de_tarefas.Model.Pessoa.PessoaRepository;
import com.api_tarefas.api_de_tarefas.Model.Pessoa.PessoaRequestDTO;
import com.api_tarefas.api_de_tarefas.Model.Pessoa.PessoaResponseDTO;
import com.api_tarefas.api_de_tarefas.Model.Tarefa.Tarefa;
import com.api_tarefas.api_de_tarefas.Model.Tarefa.TarefaRepository;
import com.api_tarefas.api_de_tarefas.Model.Tarefa.TarefaRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tarefas")
public class TarefaController {
    @Autowired
    private TarefaRepository tarefaRep;

    @Autowired
    private PessoaRepository pessoaRep;

    @GetMapping("/pendentes")
    public List<Tarefa> listaTarefaVazia(){
        return tarefaRep.get3Pendentes();
    }

    @PostMapping //cria tarefas sem pessoas alocadas e com o satus ativo
    public void addTarefa(@RequestBody TarefaRequestDTO data){
        Tarefa tarefaData = new Tarefa(data);
        tarefaData.setPessoa(null);
        tarefaData.setStatus(true);
        tarefaRep.save(tarefaData);
    }

    @PutMapping("/finalizar/{id}") //altera o status da tarefa para finalizado (false)
    public void finalizaTarefa(){
        Tarefa tarefaData = new Tarefa();
        tarefaData.setStatus(false);
        tarefaRep.save(tarefaData);
    }

    @PutMapping("/alocar/{id}")
    public void setTarefaPessoa(@PathVariable("id") Long id){
        Optional<Tarefa> tarefaOpt = tarefaRep.findById(id);

        if (tarefaOpt.isPresent()) {
            Tarefa tarefa = tarefaOpt.get();

            if (tarefa.getPessoa() == null) {
                List<Pessoa> pessoas = pessoaRep.findByDepartamento(tarefa.getDepartamento());

                if (!pessoas.isEmpty()) {
                    Pessoa pessoa = pessoas.get(0);
                    tarefa.setPessoa(pessoa);
                    tarefaRep.save(tarefa);
                } else {
                    throw new RuntimeException("Nenhuma pessoa disponível no departamento " + tarefa.getDepartamento());
                }
            } else {
                throw new RuntimeException("Tarefa já alocada para uma pessoa.");
            }
        } else {
            throw new RuntimeException("Tarefa não encontrada com ID " + id);
        }
    }
}
