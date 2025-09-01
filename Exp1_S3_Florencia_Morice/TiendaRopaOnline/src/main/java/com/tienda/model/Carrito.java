package com.tienda.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Carrito {
    private List<Producto> productos;
    
    public Carrito() {
        this.productos = new ArrayList<>();
    }
    
    public List<Producto> getProductos() {
        return Collections.unmodifiableList(productos);
    }
    
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }
    
    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }
    
    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Producto producto : productos) {
            total = total.add(producto.getPrecio());
        }
        return total;
    }
    
    public void vaciarCarrito() {
        productos.clear();
    }
    
    public int getCantidadProductos() {
        return productos.size();
    }
}