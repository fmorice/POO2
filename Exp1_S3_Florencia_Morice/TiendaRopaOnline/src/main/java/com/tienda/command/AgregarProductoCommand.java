package com.tienda.command;

import com.tienda.model.Producto;
import java.util.List;

public class AgregarProductoCommand implements Command {
    private List<Producto> productos;
    private Producto producto;
    private boolean ejecutado = false;
    
    public AgregarProductoCommand(List<Producto> productos, Producto producto) {
        this.productos = productos;
        this.producto = producto;
    }
    
    @Override
    public void ejecutar() {
        if (!productos.contains(producto)) {
            productos.add(producto);
            ejecutado = true;
        }
    }
    
    @Override
    public void deshacer() {
        if (ejecutado) {
            productos.remove(producto);
        }
    }
}