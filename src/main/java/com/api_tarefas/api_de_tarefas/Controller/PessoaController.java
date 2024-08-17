package com.api_tarefas.api_de_tarefas.Controller;

import com.api_tarefas.api_de_tarefas.Model.Departamento.DepartamentoDTO;
import com.api_tarefas.api_de_tarefas.Model.Pessoa.Pessoa;
import com.api_tarefas.api_de_tarefas.Model.Pessoa.PessoaRepository;
import com.api_tarefas.api_de_tarefas.Model.Pessoa.PessoaRequestDTO;
import com.api_tarefas.api_de_tarefas.Model.Pessoa.PessoaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pessoa")
public class PessoaController {
    @Autowired
    private PessoaRepository pessoaRep;

    @GetMapping
    public List<PessoaResponseDTO> tempMedTarefa(){
        return pessoaRep.listPessoaHoraTarefa();
    }
    @GetMapping("/gastos")
    public List<PessoaResponseDTO> listPessoa(String nome, Long inicio, Long fim){
        return pessoaRep.getMediaTarefa(nome, inicio, fim);
    }
    @PostMapping
    public void addPessoa(@RequestBody PessoaRequestDTO data){
        Pessoa pessoaData = new Pessoa(data);
        pessoaRep.save(pessoaData);
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

    @GetMapping("/departamentos")
    public List<DepartamentoDTO> listarDepartamentosComQuantidade() {
        return pessoaRep.listarDepartamentos();
    }

}
