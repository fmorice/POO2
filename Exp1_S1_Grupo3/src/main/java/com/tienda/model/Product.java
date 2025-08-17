package com.tienda.model;

/**
 * Product:
 * Representa un producto de la tienda de ropa.
 * Contiene nombre, categoría y precio.
 */
public class Product {

    private String name;       // Nombre del producto
    private String category;   // Categoría (ej: poleras, accesorios)
    private double price;      // Precio unitario

    /**
     Constructor para crear un producto
     
     */
    public Product(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    // ---------------------------
    // Getters y Setters
    // ---------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Devuelve información legible del producto
     */
    @Override
    public String toString() {
        return name + " | Categoría: " + category + " | Precio: $" + price;
    }
}
