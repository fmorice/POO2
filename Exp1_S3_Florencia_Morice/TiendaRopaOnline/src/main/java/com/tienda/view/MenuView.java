package com.tienda.view;

import com.tienda.model.Producto;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MenuView {
    private Scanner sc = new Scanner(System.in);
    private static final Locale LOCALE_CL = new Locale("es", "CL");
    private static final NumberFormat FORMATO_MONEDA = NumberFormat.getCurrencyInstance(LOCALE_CL);
    
    static {
        FORMATO_MONEDA.setMaximumFractionDigits(0);
    }
    
    public int mostrarMenu() {
        System.out.println("\n--- MENÚ TIENDA ROPA ONLINE ---");
        System.out.println("1. Ver productos");
        System.out.println("2. Aplicar descuento 10%");
        System.out.println("3. Aplicar descuento 20% para poleras");
        System.out.println("4. Gestionar carrito");
        System.out.println("5. Salir");
        System.out.print("Elige una opción: ");
        return sc.nextInt();
    }
    
    public void mostrarProductos(List<Producto> productos) {
        System.out.println("\nProductos disponibles:");
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            System.out.println((i + 1) + ". " + p.getNombre() + 
                             " - Categoria: " + p.getCategoria() + 
                             " - Precio: " + formatearPrecio(p.getPrecio()));
        }
    }
    
    public int leerNumero(String mensaje) {
        System.out.print(mensaje);
        return sc.nextInt();
    }
    
    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }
    
    // Método para formatear precios
    public String formatearPrecio(BigDecimal precio) {
        return FORMATO_MONEDA.format(precio);
    }
    
    // Método para mostrar comparativa de precios 
    public void mostrarComparativaPrecios(BigDecimal precioOriginal, BigDecimal precioDescuento, String tipoDescuento) {
        System.out.println("\n--- COMPARATIVA ---");
        System.out.println("Precio original: " + formatearPrecio(precioOriginal));
        System.out.println("Descuento: " + tipoDescuento);
        System.out.println("Precio final: " + formatearPrecio(precioDescuento));
        
        if (precioDescuento.compareTo(precioOriginal) < 0) {
            BigDecimal ahorro = precioOriginal.subtract(precioDescuento);
            System.out.println("Ahorro: " + formatearPrecio(ahorro));
        }
    }
}