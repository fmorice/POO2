package com.tienda.discount;
  // Implementación de Descuento10 con BigDecimal
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Descuento10 extends Decorator {
    public Descuento10(Component componente) {
        super(componente);
    }

    @Override
    public BigDecimal aplicarDescuento(BigDecimal precioBase) {
        BigDecimal precio = super.aplicarDescuento(precioBase);
        // Aplicar 10% de descuento
        return precio.multiply(new BigDecimal("0.90"))
                    .setScale(0, RoundingMode.HALF_UP);
    }
}