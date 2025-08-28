package com.tienda.view;

import com.tienda.model.Producto;
import java.util.List;
import java.util.Scanner;

public class MenuView {
    private Scanner sc = new Scanner(System.in);

    public int mostrarMenu() {
        System.out.println("\n--- MENÚ TIENDA ROPA ONLINE ---");
        System.out.println("1. Ver productos");
        System.out.println("2. Aplicar descuento 10%");
        System.out.println("3. Aplicar descuento 20% para poleras");
        System.out.println("4. Agregar/Eliminar productos al carrito");
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
                " - Precio: $" + p.getPrecio());
        }
    }

    public int leerNumero(String mensaje) {
        System.out.print(mensaje);
        return sc.nextInt();
    }

    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }
}
