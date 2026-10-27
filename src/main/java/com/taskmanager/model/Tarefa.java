package com.taskmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tarefas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(length = 50)
    private String prioridade; // BAIXA, MEDIA, ALTA

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    @Builder.Default
    private StatusTarefa status = StatusTarefa.PENDENTE;

    @Column(name = "data_criacao")
    @Builder.Default
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
        if (status == null) {
            status = StatusTarefa.PENDENTE;
        }
        if (prioridade == null || prioridade.isBlank()) {
            prioridade = "MEDIA";
        }
    }
}
