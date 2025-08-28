package com.tienda.command;

import com.tienda.model.Producto;
import java.util.List;

public class EliminarProductoCommand implements Command {
    private List<Producto> carrito;
    private Producto producto;

    public EliminarProductoCommand(List<Producto> carrito, Producto producto) {
        this.carrito = carrito;
        this.producto = producto;
    }

    @Override
    public void ejecutar() {
        carrito.remove(producto);
        System.out.println("❌ Producto eliminado: " + producto.getNombre());
    }
}
