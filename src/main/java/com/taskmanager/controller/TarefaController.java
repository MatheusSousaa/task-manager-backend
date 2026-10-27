package com.taskmanager.controller;

import com.taskmanager.dto.AlterarStatusRequest;
import com.taskmanager.dto.AtualizarTarefaRequest;
import com.taskmanager.dto.CriarTarefaRequest;
import com.taskmanager.exception.TarefaNaoEncontradaException;
import com.taskmanager.model.StatusTarefa;
import com.taskmanager.model.Tarefa;
import com.taskmanager.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
public class TarefaController {

    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listar(@RequestParam(required = false) StatusTarefa status) {
        if (status != null) {
            return ResponseEntity.ok(service.listarPorStatus(status));
        }
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (TarefaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody CriarTarefaRequest request) {
        try {
            Tarefa criada = service.criarTarefa(request.titulo(), request.descricao(), request.prioridade());
            return ResponseEntity.status(HttpStatus.CREATED).body(criada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody AtualizarTarefaRequest request) {
        try {
            Tarefa atualizada = service.atualizarTarefa(id, request.titulo(), request.descricao(), request.prioridade());
            return ResponseEntity.ok(atualizada);
        } catch (TarefaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> alterarStatus(@PathVariable Long id, @RequestBody AlterarStatusRequest request) {
        try {
            Tarefa atualizada = service.alterarStatus(id, request.status());
            return ResponseEntity.ok(atualizada);
        } catch (TarefaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            service.deletarTarefa(id);
            return ResponseEntity.noContent().build();
        } catch (TarefaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        }
    }
}
