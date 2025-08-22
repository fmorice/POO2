package com.mycompany.tiendaropaonline;

import com.tienda.discount.Component;
import com.tienda.discount.Descuento10;
import com.tienda.discount.DescuentoPoleras20;
import com.tienda.discount.DiscountManager;
import com.tienda.model.Producto;

import com.tienda.command.Command;
import com.tienda.command.AgregarProductoCommand;
import com.tienda.command.EliminarProductoCommand;
import com.tienda.command.Invoker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TiendaRopaOnline {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DiscountManager dm = DiscountManager.getInstance();
        Invoker invoker = new Invoker();

        // Crear productos disponibles
        Producto[] productos = {
                new Producto("Polera Básica", "poleras", 10000),
                new Producto("Cinturón Cuero", "accesorios", 8000),
                new Producto("Pantalón Jeans", "pantalones", 20000)
        };

        // Lista para el carrito
        List<Producto> carrito = new ArrayList<>();

        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- MENÚ TIENDA ROPA ONLINE ---");
            System.out.println("1. Ver productos");
            System.out.println("2. Aplicar descuento 10%");
            System.out.println("3. Aplicar descuento 20% para poleras");
            System.out.println("4. Agregar/Eliminar productos al carrito");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.println("\nProductos disponibles:");
                    for (int i = 0; i < productos.length; i++) {
                        System.out.println((i + 1) + ". " + productos[i].getNombre() +
                                " - Categoria: " + productos[i].getCategoria() +
                                " - Precio: $" + productos[i].getPrecio());
                    }
                    break;

                case 2:
                    System.out.print("Selecciona el producto (número): ");
                    int prod10 = sc.nextInt() - 1;
                    sc.nextLine();

                    if (prod10 >= 0 && prod10 < productos.length) {
                        Component conDescuento10 = new Descuento10(productos[prod10]);
                        double total = dm.calculateTotal(conDescuento10, productos[prod10].getPrecio());
                        System.out.println("Precio con 10% de descuento: $" + total);
                    } else {
                        System.out.println("Producto no válido.");
                    }
                    break;

                case 3:
                    System.out.print("Selecciona el producto (número): ");
                    int prodPolera = sc.nextInt() - 1;
                    sc.nextLine();

                    if (prodPolera >= 0 && prodPolera < productos.length) {
                        // Componente base que devuelve el precio sin descuento
                        Component base = new Component() {
                            @Override
                            public double aplicarDescuento(double precioBase) {
                                return precioBase;
                            }
                        };
                        Component conDescuentoPolera = new DescuentoPoleras20(base, productos[prodPolera]);
                        double total = dm.calculateTotal(conDescuentoPolera, productos[prodPolera].getPrecio());
                        System.out.println("Precio con 20% de descuento para poleras: $" + total);
                    } else {
                        System.out.println("Producto no válido.");
                    }
                    break;

                case 4:
                    System.out.println("1. Agregar producto al carrito");
                    System.out.println("2. Eliminar producto del carrito");
                    System.out.print("Opción: ");
                    int cmdOpcion = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Selecciona el producto (número): ");
                    int prodCmd = sc.nextInt() - 1;
                    sc.nextLine();

                    if (prodCmd >= 0 && prodCmd < productos.length) {
                        Producto productoSeleccionado = productos[prodCmd];

                        Command comando;
                        if (cmdOpcion == 1) {
                            comando = new AgregarProductoCommand(carrito, productoSeleccionado);
                        } else {
                            comando = new EliminarProductoCommand(carrito, productoSeleccionado);
                        }

                        invoker.addCommand(comando);
                        System.out.println("Comando agregado al invoker.");

                        System.out.print("¿Ejecutar comandos ahora? (s/n): ");
                        String ejecutar = sc.nextLine();
                        if (ejecutar.equalsIgnoreCase("s")) {
                            invoker.ejecutarComandos();

                            System.out.println("Carrito actual:");
                            for (Producto p : carrito) {
                                System.out.println("- " + p.getNombre() + " $" + p.getPrecio());
                            }
                        }
                    } else {
                        System.out.println("Producto no válido.");
                    }
                    break;

                case 5:
                    System.out.println("¡Gracias por visitar nuestra tienda!");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

        sc.close();
    }
}
