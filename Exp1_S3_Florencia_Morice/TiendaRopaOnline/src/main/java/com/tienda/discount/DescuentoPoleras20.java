package com.tienda.discount;

import com.tienda.model.Producto;

public class DescuentoPoleras20 extends Decorator {
    private Producto producto;

    public DescuentoPoleras20(Component componente, Producto producto) {
        super(componente);
        this.producto = producto;
    }

    @Override
    public double aplicarDescuento(double precioBase) {
        double precio = super.aplicarDescuento(precioBase);
        if ("poleras".equalsIgnoreCase(producto.getCategoria())) {
            return precio * 0.80; // 20% menos
        }
        return precio;
    }
}
