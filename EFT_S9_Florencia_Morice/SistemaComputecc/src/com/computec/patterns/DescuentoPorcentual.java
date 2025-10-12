package com.computec.patterns;

/**
 * Descuento porcentual - Implementación del patrón Decorator
 * Aplica un descuento porcentual al precio
 */
public class DescuentoPorcentual implements Descuento {
    private double porcentaje; // Porcentaje de descuento (ej: 10 para 10%)
    
    public DescuentoPorcentual(double porcentaje) {
        this.porcentaje = porcentaje;
    }
    
    @Override
    public double aplicarDescuento(double precio) {
        // Calcula el descuento y lo resta del precio original
        double descuento = precio * (porcentaje / 100.0);
        return precio - descuento;
    }
    
    public double getPorcentaje() {
        return porcentaje;
    }
    
    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
