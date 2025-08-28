package com.tienda.command;

import com.tienda.model.Producto;
import java.util.List;

public class AgregarProductoCommand implements Command {
    private List<Producto> carrito;
    private Producto producto;

    public AgregarProductoCommand(List<Producto> carrito, Producto producto) {
        this.carrito = carrito;
        this.producto = producto;
    }

    @Override
    public void ejecutar() {
        carrito.add(producto);
        System.out.println("✅ Producto agregado: " + producto.getNombre());
    }
}
