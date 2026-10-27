package com.taskmanager.exception;

public class TarefaNaoEncontradaException extends RuntimeException {
    public TarefaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
