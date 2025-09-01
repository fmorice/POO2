package com.tienda.command;

import com.tienda.model.Producto;
import java.util.List;

public class EliminarProductoCommand implements Command {
    private List<Producto> productos;
    private Producto producto;
    private boolean ejecutado = false;
    private int indice;
    
    public EliminarProductoCommand(List<Producto> productos, Producto producto) {
        this.productos = productos;
        this.producto = producto;
    }
    
    @Override
    public void ejecutar() {
        if (productos.contains(producto)) {
            indice = productos.indexOf(producto);
            productos.remove(producto);
            ejecutado = true;
        }
    }
    
    @Override
    public void deshacer() {
        if (ejecutado) {
            productos.add(indice, producto);
        }
    }
}