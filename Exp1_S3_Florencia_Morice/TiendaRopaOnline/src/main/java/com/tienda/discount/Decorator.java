package com.tienda.discount;

import java.math.BigDecimal;

public abstract class Decorator implements Component {
    protected Component componente;
    
    public Decorator(Component componente) {
        this.componente = componente;
    }
    
    @Override
    public BigDecimal aplicarDescuento(BigDecimal precioBase) {
        return componente.aplicarDescuento(precioBase);
    }
}