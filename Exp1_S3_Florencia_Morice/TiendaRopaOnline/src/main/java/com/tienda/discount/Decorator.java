package com.tienda.discount;

public abstract class Decorator implements Component {
    protected Component componente;

    public Decorator(Component componente) {
        this.componente = componente;
    }

    @Override
    public double aplicarDescuento(double precioBase) {
        return componente.aplicarDescuento(precioBase);
    }
}
