package com.tienda.model;

import com.tienda.discount.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Producto implements Component {
    private String nombre;
    private String categoria;
    private BigDecimal precio;

    public Producto(String nombre, String categoria, BigDecimal precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio.setScale(0, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal aplicarDescuento(BigDecimal precioBase) {
        // Producto base sin descuentos adicionales
        return precioBase;
    }

    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public BigDecimal getPrecio() { return precio; }
    
    public void setPrecio(BigDecimal precio) {
        this.precio = precio.setScale(0, RoundingMode.HALF_UP);
    }
    
    // Método para aplicar descuentos usando el patrón Decorator
    public BigDecimal calcularPrecioConDescuento() {
        Component productoConDescuento = this;
        return productoConDescuento.aplicarDescuento(precio);
    }
}