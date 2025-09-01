package com.tienda.command;

import java.util.Stack;

public class Invoker {
    private Stack<Command> comandosEjecutados = new Stack<>();
    
    public void addCommand(Command comando) {
        comandosEjecutados.push(comando);
    }
    
    public void ejecutarComandos() {
        while (!comandosEjecutados.isEmpty()) {
            Command comando = comandosEjecutados.pop();
            comando.ejecutar();
        }
    }
    
    public void deshacerUltimoComando() {
        if (!comandosEjecutados.isEmpty()) {
            Command comando = comandosEjecutados.pop();
            comando.deshacer();
        }
    }
}