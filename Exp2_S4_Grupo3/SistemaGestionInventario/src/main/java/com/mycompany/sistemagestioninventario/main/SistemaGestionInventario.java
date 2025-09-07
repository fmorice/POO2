package com.mycompany.sistemagestioninventario.main;

import com.mycompany.sistemagestioninventario.model.Inventario;
import com.mycompany.sistemagestioninventario.model.Producto;

/**
 * Clase principal de la aplicación de Sistema de Gestión de Inventario
 * Punto de entrada del sistema - MyCompany Solutions
 */
public class SistemaGestionInventario {
    
    private static Inventario inventario;
    private static MenuPrincipal menuPrincipal;
    
    /**
     * Método main - Punto de entrada de la aplicación
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        mostrarBannerInicial();
        
        try {
            inicializarSistema();
            ejecutarAplicacion();
        } catch (Exception e) {
            manejarErrorCritico(e);
        } finally {
            mostrarMensajeDespedida();
        }
    }
    
    /**
     * Muestra el banner inicial de la aplicación
     */
    private static void mostrarBannerInicial() {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║ 🚀 SISTEMA DE GESTIÓN DE INVENTARIO - MyCompany   ║");
        System.out.println("║                Versión 1.0.0                       ║");
        System.out.println("║                                                    ║");
        System.out.println("║ 📦 Gestión completa de productos                   ║");
        System.out.println("║ 🔍 Búsquedes avanzadas                            ║");
        System.out.println("║ 📊 Reportes detallados                            ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println();
    }
    
    /**
     * Inicializa los componentes del sistema
     */
    private static void inicializarSistema() {
        System.out.println("🔄 Inicializando sistema...");
        
        inventario = new Inventario();
        menuPrincipal = new MenuPrincipal();
        
        // Agregar algunos productos de ejemplo
        agregarProductosEjemplo();
        
        System.out.println("✅ Sistema inicializado correctamente");
        System.out.println("📊 Productos cargados: " + inventario.obtenerCantidadProductos());
    }
    
    /**
     * Ejecuta la aplicación principal
     */
    private static void ejecutarAplicacion() {
        System.out.println("\n🎯 Iniciando interfaz de usuario...");
        menuPrincipal.iniciar();
    }
    
    /**
     * Maneja errores críticos de la aplicación
     */
    private static void manejarErrorCritico(Exception e) {
        System.out.println("\n❌💥 ERROR CRÍTICO EN LA APLICACIÓN");
        System.out.println("==============================================");
        System.out.println("Mensaje: " + e.getMessage());
        System.out.println("Tipo: " + e.getClass().getSimpleName());
        System.out.println("==============================================");
        System.out.println("⚠️  Por favor, contacte al soporte técnico de MyCompany");
    }
    
    /**
     * Muestra mensaje de despedida
     */
    private static void mostrarMensajeDespedida() {
        System.out.println("\n==============================================");
        System.out.println("👋 Sesión finalizada - Sistema de Gestión de Inventario");
        System.out.println("📍 MyCompany - Soluciones Tecnológicas");
        System.out.println("📧 contacto@mycompany.com");
        System.out.println("🌐 www.mycompany.com");
        System.out.println("==============================================");
    }
    
    /**
     * Agrega productos de ejemplo al sistema
     */
    private static void agregarProductosEjemplo() {
        try {
            inventario.agregarProducto(new Producto("LAP001", "Laptop HP EliteBook", 
                "Laptop empresarial 14\" Intel i7, 16GB RAM, 512GB SSD", 1299.99, 8, "Tecnología"));
            
            inventario.agregarProducto(new Producto("MON002", "Monitor Dell 27\"", 
                "Monitor LED 27 pulgadas 4K UHD", 349.99, 12, "Tecnología"));
            
            inventario.agregarProducto(new Producto("TEC003", "Teclado Mecánico RGB", 
                "Teclado gaming mecánico con retroiluminación RGB", 89.99, 25, "Tecnología"));
            
            inventario.agregarProducto(new Producto("SIL004", "Silla Ergonómica Ejecutiva", 
                "Silla de oficina ergonómica con soporte lumbar", 299.99, 5, "Mobiliario"));
            
            inventario.agregarProducto(new Producto("ESC005", "Escritorio Moderno", 
                "Escritorio de oficina 160x80cm de madera", 199.99, 3, "Mobiliario"));
                
        } catch (Exception e) {
            System.out.println("⚠️  Error al cargar productos de ejemplo: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene la instancia del inventario (para posibles extensiones)
     */
    public static Inventario getInventario() {
        return inventario;
    }
    
    /**
     * Obtiene la instancia del menú principal (para posibles extensiones)
     */
    public static MenuPrincipal getMenuPrincipal() {
        return menuPrincipal;
    }
}