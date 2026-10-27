package com.taskmanager.dto;

public record AtualizarTarefaRequest(
    String titulo,
    String descricao,
    String prioridade
) {}
