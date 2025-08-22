package com.tienda.command;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
    private List<Command> comandos = new ArrayList<>();

    public void addCommand(Command command) {
        comandos.add(command);
    }

    public void ejecutarComandos() {
        for (Command c : comandos) {
            c.ejecutar();
        }
        comandos.clear(); // limpia después de ejecutar
    }
}
