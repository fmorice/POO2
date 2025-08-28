package com.tienda.discount;

public class Descuento10 extends Decorator {
    public Descuento10(Component componente) {
        super(componente);
    }

    @Override
    public double aplicarDescuento(double precioBase) {
        double precio = super.aplicarDescuento(precioBase);
        return precio * 0.90; // 10% menos
    }
}
