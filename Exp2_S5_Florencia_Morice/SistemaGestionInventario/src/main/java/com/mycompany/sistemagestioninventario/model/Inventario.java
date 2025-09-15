package com.mycompany.sistemagestioninventario.model;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

// Clase que gestiona una colección de productos mediante una estructura HashMap. Incluye métodos para agregar, eliminar, buscar productos y listar todos los productos disponibles.
 
public class Inventario {
    private Map<String, Producto> productos;
    
    // Constructor que inicializa el inventario vacío
    public Inventario() {
        this.productos = new HashMap<>();
    }
         
    public boolean agregarProducto(Producto producto) {
        if (producto == null) {
            System.out.println("Error: El producto no puede ser nulo.");
            return false;
        }
        
        if (productos.containsKey(producto.getId())) {
            System.out.println("Error: Ya existe un producto con el ID " + producto.getId());
            return false;
        }
        
        productos.put(producto.getId(), producto);
        System.out.println(" Producto agregado: " + producto.getNombre());
        return true;
    }
         
    public boolean eliminarProducto(String id) {
        if (!productos.containsKey(id)) {
            System.out.println("Error: No existe un producto con el ID " + id);
            return false;
        }
        
        Producto eliminado = productos.remove(id);
        System.out.println(" Producto eliminado: " + eliminado.getNombre());
        return true;
    }
    
    public List<Producto> buscarProductosPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Error: El nombre de búsqueda no puede estar vacío.");
            return new ArrayList<>();
        }
        
        String nombreBusqueda = nombre.toLowerCase().trim();
        List<Producto> resultados = productos.values().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombreBusqueda))
                .collect(Collectors.toList());
        
        System.out.println(" Búsqueda por nombre '" + nombre + "': " + resultados.size() + " resultados");
        return resultados;
    }
    
    public Producto buscarProductoPorId(String id) {
        Producto producto = productos.get(id);
        if (producto == null) {
            System.out.println(" No se encontró producto con ID: " + id);
        } else {
            System.out.println(" Producto encontrado: " + producto.getNombre());
        }
        return producto;
    }
    
    public List<Producto> listarTodosProductos() {
        return new ArrayList<>(productos.values());
    }
    
    public Collection<Producto> obtenerTodosProductos() {
        return productos.values();
    }
    
    public boolean existeProducto(String id) {
        return productos.containsKey(id);
    }
         
    public int obtenerCantidadProductos() {
        return productos.size();
    }
    
    //Verifica si el inventario está vacío
    
    public boolean estaVacio() {
        return productos.isEmpty();
    }
    
    //Actualiza la información de un producto existente     
    public boolean actualizarProducto(Producto producto) {
        if (producto == null) {
            System.out.println("Error: El producto no puede ser nulo.");
            return false;
        }
        
        if (!productos.containsKey(producto.getId())) {
            System.out.println("Error: No existe un producto con el ID " + producto.getId());
            return false;
        }
        
        productos.put(producto.getId(), producto);
        System.out.println(" Producto actualizado: " + producto.getNombre());
        return true;
    }
    
    public List<Producto> buscarProductosPorCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            System.out.println("Error: La categoría no puede estar vacía.");
            return new ArrayList<>();
        }
        
        String categoriaBusqueda = categoria.toLowerCase().trim();
        List<Producto> resultados = productos.values().stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoriaBusqueda))
                .collect(Collectors.toList());
        
        System.out.println(" Búsqueda por categoría '" + categoria + "': " + resultados.size() + " resultados");
        return resultados;
    }
    
    public List<Producto> buscarProductosStockBajo(int umbral) {
        if (umbral < 0) {
            System.out.println("Error: El umbral no puede ser negativo.");
            return new ArrayList<>();
        }
        
        List<Producto> resultados = productos.values().stream()
                .filter(p -> p.getCantidadStock() < umbral)
                .collect(Collectors.toList());
        
        System.out.println("Productos con stock bajo (" + umbral + "): " + resultados.size() + " productos");
        return resultados;
    }
    
    public void mostrarInventarioCompleto() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println(" INVENTARIO COMPLETO");
        System.out.println("=".repeat(80));
        
        if (productos.isEmpty()) {
            System.out.println("El inventario está vacío.");
            return;
        }
        
        System.out.println("Total de productos: " + productos.size());
        System.out.printf("Valor total del inventario: $%,.2f%n", calcularValorTotalInventario());
        System.out.println("-".repeat(80));
        
        // Listar todos los productos con información detallada
        productos.values().forEach(producto -> {
            System.out.println("🔹 " + producto.obtenerDescripcionDetallada());
            System.out.println("-".repeat(80));
        });
    }
    
    //Muestra un resumen del inventario
     
    public void mostrarResumenInventario() {
        System.out.println("\n📊 RESUMEN DEL INVENTARIO");
        System.out.println("=".repeat(50));
        System.out.println("Total productos: " + productos.size());
        System.out.printf("Valor total: $%,.2f%n", calcularValorTotalInventario());
        System.out.println("Productos con stock bajo: " + buscarProductosStockBajo(5).size());
        
        // Mostrar productos por categoría
        Map<String, Long> productosPorCategoria = productos.values().stream()
                .collect(Collectors.groupingBy(
                    Producto::getCategoria,
                    Collectors.counting()
                ));
        
        if (!productosPorCategoria.isEmpty()) {
            System.out.println("\n🏷️  DISTRIBUCIÓN POR CATEGORÍA:");
            productosPorCategoria.forEach((categoria, cantidad) -> {
                System.out.printf("• %s: %d productos%n", categoria, cantidad);
            });
        }
    }
    
    public double calcularValorTotalInventario() {
        return productos.values().stream()
                .mapToDouble(Producto::calcularValorTotalStock)
                .sum();
    }
         
    public void limpiarInventario() {
        int cantidad = productos.size();
        productos.clear();
        System.out.println("✅ Inventario limpiado. " + cantidad + " productos eliminados.");
    }
    
    //Obtiene todos los IDs de los productos en el inventario     
    public List<String> obtenerTodosIds() {
        return new ArrayList<>(productos.keySet());
    }
}

