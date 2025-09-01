package com.tienda.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DescuentoPoleras20 extends Decorator {
    private String categoria; 
    
    public DescuentoPoleras20(Component componente, String categoria) {
        super(componente);
        this.categoria = categoria;
    }

    @Override
    public BigDecimal aplicarDescuento(BigDecimal precioBase) {
        BigDecimal precio = super.aplicarDescuento(precioBase);
        
        // Aplicar 20% de descuento solo para poleras
        if ("poleras".equalsIgnoreCase(categoria)) {
            return precio.multiply(new BigDecimal("0.80"))
                        .setScale(0, RoundingMode.HALF_UP);
        }
        return precio;
    }
}