package com.tienda.model;

import com.tienda.discount.Component;

public class Producto implements Component {
    private String nombre;
    private String categoria;
    private double precio;

    public Producto(String nombre, String categoria, double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    @Override
    public double aplicarDescuento(double precioBase) {
        return precioBase; // sin descuento por defecto
    }

    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio() { return precio; }
}
