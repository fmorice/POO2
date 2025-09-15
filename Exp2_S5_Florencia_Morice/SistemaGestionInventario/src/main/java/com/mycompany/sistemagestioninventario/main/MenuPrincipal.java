package com.mycompany.sistemagestioninventario.main;

import com.mycompany.sistemagestioninventario.model.Producto;
import com.mycompany.sistemagestioninventario.model.Inventario;
import java.util.Scanner;
import java.util.List;

/**
 * Clase MenuPrincipal que proporciona la interfaz de usuario por consola
 * para interactuar con el sistema de gestión de inventario.
 */
public class MenuPrincipal {
    private Inventario inventario;
    private Scanner scanner;
    private boolean ejecutando;
    
    /**
     * Constructor que inicializa el inventario y el scanner
     */
    public MenuPrincipal() {
        this.inventario = new Inventario();
        this.scanner = new Scanner(System.in);
        this.ejecutando = true;
        agregarProductosEjemplo();
    }
    
    /**
     * Método que inicia la interfaz de usuario
     */
    public void iniciar() {
        while (ejecutando) {
            mostrarMenuPrincipal();
            int opcion = obtenerOpcionUsuario();
            procesarOpcion(opcion);
        }
        scanner.close();
    }
    
    /**
     * Muestra el menú principal de opciones
     */
    private void mostrarMenuPrincipal() {
        System.out.println("\n📋 MENÚ PRINCIPAL - SISTEMA DE GESTIÓN DE INVENTARIO");
        System.out.println("====================================================");
        System.out.println("1. 📦 Agregar nuevo producto");
        System.out.println("2. 🗑️  Eliminar producto");
        System.out.println("3. 🔍 Buscar productos por nombre");
        System.out.println("4. 🔎 Buscar producto por ID");
        System.out.println("5. 🏷️  Buscar productos por categoría");
        System.out.println("6. 📊 Listar todos los productos");
        System.out.println("7. ⚠️  Ver productos con stock bajo");
        System.out.println("8. ✏️  Actualizar producto");
        System.out.println("9. 📈 Mostrar resumen del inventario");
        System.out.println("10. 🗂️ Mostrar inventario completo");
        System.out.println("11. 🧹 Limpiar inventario");
        System.out.println("0. 🚪 Salir");
        System.out.print("Seleccione una opción (0-11): ");
    }
    
    /**
     * Obtiene y valida la opción del usuario
     */
    private int obtenerOpcionUsuario() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Procesa la opción seleccionada por el usuario
     */
    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> agregarProducto();
            case 2 -> eliminarProducto();
            case 3 -> buscarProductosPorNombre();
            case 4 -> buscarProductoPorId();
            case 5 -> buscarProductosPorCategoria();
            case 6 -> listarTodosProductos();
            case 7 -> verProductosStockBajo();
            case 8 -> actualizarProducto();
            case 9 -> mostrarResumenInventario();
            case 10 -> mostrarInventarioCompleto();
            case 11 -> limpiarInventario();
            case 0 -> ejecutando = false;
            default -> System.out.println("❌ Opción inválida. Por favor, seleccione 0-11.");
        }
        
        if (opcion != 0) {
            System.out.print("\nPresione Enter para continuar...");
            scanner.nextLine();
        }
    }
    
    // Los métodos de operaciones (agregarProducto, eliminarProducto, etc.)
    // se mantienen exactamente igual que en la versión anterior
    
    private void agregarProducto() {
        System.out.println("\n📦 AGREGAR NUEVO PRODUCTO");
        System.out.println("========================");
        
        String id = obtenerEntrada("ID del producto: ", true);
        if (inventario.existeProducto(id)) {
            System.out.println("❌ Ya existe un producto con ese ID.");
            return;
        }
        if (id.isEmpty()) {
        System.out.println("❌ ERROR: El ID no puede estar vacío");
        return; // Detiene la operación
    }
        String nombre = obtenerEntrada("Nombre: ", true);
        String descripcion = obtenerEntrada("Descripción: ", false);
        double precio = obtenerPrecio();
        int cantidadStock = obtenerCantidadStock();
        String categoria = obtenerEntrada("Categoría: ", true);
        
        Producto nuevoProducto = new Producto(id, nombre, descripcion, precio, cantidadStock, categoria);
        
        if (inventario.agregarProducto(nuevoProducto)) {
            System.out.println("✅ Producto agregado exitosamente!");
        }
    }
    
    private void eliminarProducto() {
        System.out.println("\n🗑️  ELIMINAR PRODUCTO");
        System.out.println("===================");
        
        String id = obtenerEntrada("ID del producto a eliminar: ", true);
        
        if (inventario.eliminarProducto(id)) {
            System.out.println("✅ Producto eliminado exitosamente!");
        }
    }
    
    private void buscarProductosPorNombre() {
        System.out.println("\n🔍 BUSCAR PRODUCTOS POR NOMBRE");
        System.out.println("=============================");
        
        String nombre = obtenerEntrada("Nombre a buscar: ", true);
        List<Producto> resultados = inventario.buscarProductosPorNombre(nombre);
        mostrarResultadosBusqueda(resultados, "nombre '" + nombre + "'");
    }
    
    private void buscarProductoPorId() {
        System.out.println("\n🔎 BUSCAR PRODUCTO POR ID");
        System.out.println("========================");
        
        String id = obtenerEntrada("ID del producto: ", true);
        Producto producto = inventario.buscarProductoPorId(id);
        
        if (producto != null) {
            System.out.println("\n✅ PRODUCTO ENCONTRADO:");
            System.out.println("======================");
            System.out.println(producto.obtenerDescripcionDetallada());
        }
    }
    
    private void buscarProductosPorCategoria() {
        System.out.println("\n🏷️  BUSCAR PRODUCTOS POR CATEGORÍA");
        System.out.println("================================");
        
        String categoria = obtenerEntrada("Categoría a buscar: ", true);
        List<Producto> resultados = inventario.buscarProductosPorCategoria(categoria);
        mostrarResultadosBusqueda(resultados, "categoría '" + categoria + "'");
    }
    
    private void listarTodosProductos() {
        System.out.println("\n📊 LISTA DE TODOS LOS PRODUCTOS");
        System.out.println("==============================");
        
        List<Producto> productos = inventario.listarTodosProductos();
        
        if (productos.isEmpty()) {
            System.out.println("El inventario está vacío.");
            return;
        }
        
        System.out.println("Total de productos: " + productos.size());
        System.out.println("----------------------------------------");
        
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            System.out.println((i + 1) + ". " + p.obtenerInfoResumida());
        }
    }
    
    private void verProductosStockBajo() {
        System.out.println("\n⚠️  PRODUCTOS CON STOCK BAJO");
        System.out.println("=========================");
        
        int umbral = obtenerUmbralStock();
        List<Producto> productosBajoStock = inventario.buscarProductosStockBajo(umbral);
        mostrarResultadosBusqueda(productosBajoStock, "stock bajo (< " + umbral + " unidades)");
    }
    
    private void actualizarProducto() {
        System.out.println("\n✏️  ACTUALIZAR PRODUCTO");
        System.out.println("=====================");
        
        String id = obtenerEntrada("ID del producto a actualizar: ", true);
        Producto productoExistente = inventario.buscarProductoPorId(id);
        
        if (productoExistente == null) return;
        
        System.out.println("\nProducto actual:");
        System.out.println(productoExistente.obtenerDescripcionDetallada());
        System.out.println("\nIngrese los nuevos datos (deje en blanco para mantener el valor actual):");
        
        String nombre = obtenerEntradaOpcional("Nuevo nombre [" + productoExistente.getNombre() + "]: ", productoExistente.getNombre());
        String descripcion = obtenerEntradaOpcional("Nueva descripción [" + productoExistente.getDescripcion() + "]: ", productoExistente.getDescripcion());
        double precio = obtenerPrecioOpcional(productoExistente.getPrecio());
        int cantidadStock = obtenerCantidadStockOpcional(productoExistente.getCantidadStock());
        String categoria = obtenerEntradaOpcional("Nueva categoría [" + productoExistente.getCategoria() + "]: ", productoExistente.getCategoria());
        
        Producto productoActualizado = new Producto(id, nombre, descripcion, precio, cantidadStock, categoria);
        
        if (inventario.actualizarProducto(productoActualizado)) {
            System.out.println("✅ Producto actualizado exitosamente!");
        }
    }
    
    private void mostrarResumenInventario() {
        System.out.println("\n📈 RESUMEN DEL INVENTARIO");
        System.out.println("========================");
        inventario.mostrarResumenInventario();
    }
    
    private void mostrarInventarioCompleto() {
        inventario.mostrarInventarioCompleto();
    }
    
    private void limpiarInventario() {
        System.out.println("\n🧹 LIMPIAR INVENTARIO");
        System.out.println("====================");
        
        String confirmacion = obtenerEntrada("¿Está seguro? Esta acción no se puede deshacer. (s/n): ", true);
        
        if (confirmacion.equalsIgnoreCase("s")) {
            inventario.limpiarInventario();
            System.out.println("✅ Inventario limpiado exitosamente!");
        } else {
            System.out.println("❌ Operación cancelada.");
        }
    }
    
    // Métodos auxiliares (se mantienen igual)
    private String obtenerEntrada(String mensaje, boolean requerido) {
        String entrada;
        do {
            System.out.print(mensaje);
            entrada = scanner.nextLine().trim();
            if (requerido && entrada.isEmpty()) {
                System.out.println("❌ Este campo es requerido.");
            }
        } while (requerido && entrada.isEmpty());
        return entrada;
    }
    
    private String obtenerEntradaOpcional(String mensaje, String valorActual) {
        System.out.print(mensaje);
        String entrada = scanner.nextLine().trim();
        return entrada.isEmpty() ? valorActual : entrada;
    }
    
    private double obtenerPrecio() {
        while (true) {
            try {
                System.out.print("Precio: ");
                double precio = Double.parseDouble(scanner.nextLine());
                if (precio >= 0) return precio;
                System.out.println("❌ El precio no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número válido.");
            }
        }
    }
    
    private double obtenerPrecioOpcional(double valorActual) {
        while (true) {
            try {
                System.out.print("Nuevo precio [" + valorActual + "]: ");
                String entrada = scanner.nextLine().trim();
                if (entrada.isEmpty()) return valorActual;
                double precio = Double.parseDouble(entrada);
                if (precio >= 0) return precio;
                System.out.println("❌ El precio no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número válido.");
            }
        }
    }
    
    private int obtenerCantidadStock() {
        while (true) {
            try {
                System.out.print("Cantidad en stock: ");
                int stock = Integer.parseInt(scanner.nextLine());
                if (stock >= 0) return stock;
                System.out.println("❌ La cantidad no puede ser negativa.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número entero válido.");
            }
        }
    }
    
    private int obtenerCantidadStockOpcional(int valorActual) {
        while (true) {
            try {
                System.out.print("Nueva cantidad en stock [" + valorActual + "]: ");
                String entrada = scanner.nextLine().trim();
                if (entrada.isEmpty()) return valorActual;
                int stock = Integer.parseInt(entrada);
                if (stock >= 0) return stock;
                System.out.println("❌ La cantidad no puede ser negativa.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número entero válido.");
            }
        }
    }
    
    private int obtenerUmbralStock() {
        while (true) {
            try {
                System.out.print("Umbral de stock bajo: ");
                int umbral = Integer.parseInt(scanner.nextLine());
                if (umbral >= 0) return umbral;
                System.out.println("❌ El umbral no puede ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor, ingrese un número entero válido.");
            }
        }
    }
    
    private void mostrarResultadosBusqueda(List<Producto> resultados, String criterio) {
        if (resultados.isEmpty()) {
            System.out.println("❌ No se encontraron productos con " + criterio + ".");
            return;
        }
        
        System.out.println("\n✅ Se encontraron " + resultados.size() + " productos con " + criterio + ":");
        System.out.println("=" .repeat(60));
        
        for (int i = 0; i < resultados.size(); i++) {
            Producto p = resultados.get(i);
            System.out.println((i + 1) + ". " + p.obtenerDescripcionDetallada());
            System.out.println("-".repeat(60));
        }
    }
    
    private void agregarProductosEjemplo() {
        inventario.agregarProducto(new Producto("P001", "Laptop HP", "Laptop HP 15.6 pulgadas, 8GB RAM", 899.99, 10, "Tecnología"));
        inventario.agregarProducto(new Producto("P002", "Mouse Inalámbrico", "Mouse óptico inalámbrico", 25.50, 50, "Tecnología"));
        inventario.agregarProducto(new Producto("P003", "Teclado Mecánico", "Teclado mecánico RGB", 89.99, 15, "Tecnología"));
        inventario.agregarProducto(new Producto("P004", "Monitor 24\"", "Monitor LED 24 pulgadas Full HD", 199.99, 8, "Tecnología"));
        inventario.agregarProducto(new Producto("P005", "Silla Oficina", "Silla ergonómica para oficina", 159.99, 3, "Muebles"));
        inventario.agregarProducto(new Producto("P006", "Escritorio", "Escritorio de madera 120x60cm", 129.99, 2, "Muebles"));
    }
}