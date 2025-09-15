package com.mycompany.sistemagestioninventario.model;

import java.util.Date;
import java.util.Objects;

// Clase que representa un producto en el sistema de gestión de inventario. Incluye atributos básicos como ID, nombre, descripción, precio y cantidad en stock.
public class Producto {
    //  Atributos de la clase
    private String id; 
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidadStock;
    private String categoria;
    private Date fechaCreacion;
    private Date fechaUltimaActualizacion;
    
    // Constructor completo de la clase Producto
      public Producto(String id, String nombre, String descripcion, 
                   double precio, int cantidadStock, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.categoria = categoria;
        this.fechaCreacion = new Date();
        this.fechaUltimaActualizacion = new Date();
    }
    
   //Constructor simplificado para creación rápida
     public Producto(String id, String nombre, double precio, 
                   int cantidadStock, String categoria) {
        this(id, nombre, "", precio, cantidadStock, categoria);
    }
    
    // Getters y setters con validaciones básicas
    public String getId() { // Cambiado de getCodigo() a getId()
        return id; 
    }
    
    public void setId(String id) { // Cambiado de setCodigo() a setId()
        if (id != null && !id.trim().isEmpty()) {
            this.id = id;
            actualizarFechaModificacion();
        }
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
            actualizarFechaModificacion();
        }
    }
    
    public String getDescripcion() { 
        return descripcion; 
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        actualizarFechaModificacion();
    }
    
    public double getPrecio() { 
        return precio; 
    }
    
    public void setPrecio(double precio) {
        if (precio >= 0) {
            this.precio = precio;
            actualizarFechaModificacion();
        }
    }
    
    public int getCantidadStock() { 
        return cantidadStock; 
    }
    
    public void setCantidadStock(int cantidadStock) {
        if (cantidadStock >= 0) {
            this.cantidadStock = cantidadStock;
            actualizarFechaModificacion();
        }
    }
    
    public String getCategoria() { 
        return categoria; 
    }
    
    public void setCategoria(String categoria) {
        if (categoria != null && !categoria.trim().isEmpty()) {
            this.categoria = categoria;
            actualizarFechaModificacion();
        }
    }
    
    public Date getFechaCreacion() { 
        return new Date(fechaCreacion.getTime()); 
    }
    
    public Date getFechaUltimaActualizacion() { 
        return new Date(fechaUltimaActualizacion.getTime()); 
    }
    
    //  Método para actualizar el precio del producto con validación
    public boolean actualizarPrecio(double nuevoPrecio) {
        if (nuevoPrecio >= 0) {
            this.precio = nuevoPrecio;
            actualizarFechaModificacion();
            return true;
        }
        return false;
    }
    public boolean actualizarStock(int nuevaCantidad) {
        if (nuevaCantidad >= 0) {
            this.cantidadStock = nuevaCantidad;
            actualizarFechaModificacion();
            return true;
        }
        return false;
    }
    
    public boolean agregarStock(int cantidad) {
        if (cantidad > 0) {
            this.cantidadStock += cantidad;
            actualizarFechaModificacion();
            return true;
        }
        return false;
    }
        
    public boolean retirarStock(int cantidad) {
        if (cantidad > 0 && cantidad <= cantidadStock) {
            this.cantidadStock -= cantidad;
            actualizarFechaModificacion();
            return true;
        }
        return false;
    }
    
    public double calcularValorTotalStock() {
        return precio * cantidadStock;
    }
    
    public boolean tieneStockBajo(int umbral) {
        return cantidadStock < umbral;
    }
         
    public String obtenerDescripcionDetallada() {
        return String.format(
            "ID: %s | Nombre: %s | Descripción: %s | " + // Cambiado "Código" por "ID"
            "Precio: $%.2f | Stock: %d | Categoría: %s | " +
            "Creado: %s | Actualizado: %s",
            id, nombre, descripcion, precio, cantidadStock, categoria,
            fechaCreacion, fechaUltimaActualizacion
        );
    }
    
    public String obtenerInfoResumida() {
        return String.format("%s - %s ($%.2f) [Stock: %d]", 
                            id, nombre, precio, cantidadStock); // Cambiado codigo por id
    }
    
    private void actualizarFechaModificacion() {
        this.fechaUltimaActualizacion = new Date();
    }
    
    // Métodos equals y hashCode para comparación por ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(id, producto.id); 
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id); 
    }
    
    @Override
    public String toString() {
        return obtenerInfoResumida();
    }
    
    // Método estático para validar un ID de producto
    public static boolean validarId(String id) { // Cambiado de validarCodigo() a validarId()
        return id != null && !id.trim().isEmpty() && id.matches("[A-Za-z0-9-]+");
    }
         
    public static boolean validarPrecio(double precio) {
        return precio >= 0;
    }     
    public static boolean validarStock(int stock) {
        return stock >= 0;
    }
}
