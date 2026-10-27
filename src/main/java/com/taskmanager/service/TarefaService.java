package com.taskmanager.service;

import com.taskmanager.exception.TarefaNaoEncontradaException;
import com.taskmanager.model.StatusTarefa;
import com.taskmanager.model.Tarefa;
import com.taskmanager.repository.TarefaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository repository;

    public TarefaService(TarefaRepository repository) {
        this.repository = repository;
    }

    public List<Tarefa> listarTodas() {
        return repository.findAll();
    }

    public List<Tarefa> listarPorStatus(StatusTarefa status) {
        return repository.findByStatus(status);
    }

    public Tarefa buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TarefaNaoEncontradaException("Tarefa com ID " + id + " não encontrada!"));
    }

    @Transactional
    public Tarefa criarTarefa(String titulo, String descricao, String prioridade) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título da tarefa não pode ser vazio!");
        }
        if (prioridade == null || prioridade.trim().isEmpty()) {
            prioridade = "MEDIA";
        }

        Tarefa novaTarefa = Tarefa.builder()
                .titulo(titulo.trim())
                .descricao(descricao != null ? descricao.trim() : "")
                .prioridade(prioridade.toUpperCase().trim())
                .status(StatusTarefa.PENDENTE)
                .build();

        return repository.save(novaTarefa);
    }

    @Transactional
    public Tarefa atualizarTarefa(Long id, String novoTitulo, String novaDescricao, String novaPrioridade) {
        Tarefa tarefa = buscarPorId(id);

        if (novoTitulo != null && !novoTitulo.trim().isEmpty()) {
            tarefa.setTitulo(novoTitulo.trim());
        }
        if (novaDescricao != null) {
            tarefa.setDescricao(novaDescricao.trim());
        }
        if (novaPrioridade != null && !novaPrioridade.trim().isEmpty()) {
            tarefa.setPrioridade(novaPrioridade.toUpperCase().trim());
        }

        return repository.save(tarefa);
    }

    @Transactional
    public Tarefa alterarStatus(Long id, StatusTarefa novoStatus) {
        Tarefa tarefa = buscarPorId(id);
        tarefa.setStatus(novoStatus);
        return repository.save(tarefa);
    }

    @Transactional
    public void deletarTarefa(Long id) {
        if (!repository.existsById(id)) {
            throw new TarefaNaoEncontradaException("Não foi possível excluir: Tarefa com ID " + id + " não existe!");
        }
        repository.deleteById(id);
    }
}
