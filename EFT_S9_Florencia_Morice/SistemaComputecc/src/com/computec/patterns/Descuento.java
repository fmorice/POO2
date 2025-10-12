package com.computec.patterns;

/**
 * Interfaz Descuento - Patrón Decorator
 * Define un contrato para aplicar descuentos a precios
 */
public interface Descuento {
    /**
     * Aplica un descuento al precio
     * @param precio Precio original
     * @return Precio con descuento aplicado
     */
    double aplicarDescuento(double precio);
}
