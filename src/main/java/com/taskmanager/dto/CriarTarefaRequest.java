package com.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;

public record CriarTarefaRequest(
    @NotBlank(message = "O título é obrigatório")
    String titulo,
    String descricao,
    String prioridade
) {}
