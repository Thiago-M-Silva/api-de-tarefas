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

@RestController
@RequestMapping("pessoa")
public class PessoaController {
    @Autowired
    private PessoaRepository pessoaRep;

    @Autowired
    private TarefaRepository tarefaRep;

    @GetMapping
    public List<PessoaResponseDTO> listPessoa(){
        List<PessoaResponseDTO> pessoaList = pessoaRep.findAll().stream().map(PessoaResponseDTO::new).toList();
        return pessoaList;
    }

    @PostMapping
    public void addPessoa(@RequestBody PessoaRequestDTO data){
        Pessoa pessoaData = new Pessoa(data);
        pessoaRep.save(pessoaData);
    }


    @PostMapping
    public void addTarefa(@RequestBody TarefaRequestDTO data){
        Tarefa tarefaData = new Tarefa(data);
        tarefaRep.save(tarefaData);
    }

    @PutMapping("/{id}")
    public void updatePessoa(@PathVariable("id") Long id, @RequestBody PessoaRequestDTO data){
        Pessoa pessoaData = new Pessoa(data);
        pessoaRep.deleteById(data.id());
        pessoaRep.save(pessoaData);
    }

    @DeleteMapping("/{id}")
    public void removePessoa(@PathVariable("id") Long id){
        pessoaRep.deleteById(id);
    }

//    @GetMapping
//    public void addPessoa(){} listar departamentos, pessoas e qtd de tarefas
//
//    @GetMapping
//    public void addPessoa(){} listar 3 tarefas sem pessoas em ordem decrescente de prazo
//
//    @PutMapping
//    public void setPessoa(){} alocar pessoa em uma tarefa para o msm dep
//
//    @PutMapping
//    public void setPessoa(){} finalizar tarefa

}
