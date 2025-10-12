package com.computec.patterns;

/**
 * Sin descuento - Implementación básica del patrón Decorator
 * Retorna el precio sin modificar
 */
public class SinDescuento implements Descuento {
    
    @Override
    public double aplicarDescuento(double precio) {
        // No aplica descuento, retorna el precio original
        return precio;
    }
}
