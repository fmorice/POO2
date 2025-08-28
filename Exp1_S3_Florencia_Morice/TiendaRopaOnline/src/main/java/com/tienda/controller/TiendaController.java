package com.tienda.controller;

import com.tienda.model.Carrito;
import com.tienda.model.Producto;
import com.tienda.view.MenuView;
import com.tienda.discount.*;
import com.tienda.command.*;

import java.util.Arrays;
import java.util.List;

public class TiendaController {
    private List<Producto> productos;
    private Carrito carrito;
    private MenuView view;
    private DiscountManager dm;
    private Invoker invoker;

    public TiendaController() {
        this.productos = Arrays.asList(
                new Producto("Polera Básica", "poleras", 10000),
                new Producto("Cinturón Cuero", "accesorios", 8000),
                new Producto("Pantalón Jeans", "pantalones", 20000)
        );
        this.carrito = new Carrito();
        this.view = new MenuView();
        this.dm = DiscountManager.getInstance();
        this.invoker = new Invoker();
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            int opcion = view.mostrarMenu();
            switch (opcion) {
                case 1:
                    view.mostrarProductos(productos);
                    break;
                case 2:
                    aplicarDescuento10();
                    break;
                case 3:
                    aplicarDescuentoPoleras();
                    break;
                case 4:
                    gestionarCarrito();
                    break;
                case 5:
                    salir = true;
                    view.mostrarMensaje("¡Gracias por visitar nuestra tienda!");
                    break;
                default:
                    view.mostrarMensaje("Opción inválida");
            }
        }
    }

    private void aplicarDescuento10() {
        int index = view.leerNumero("Selecciona el producto (número): ") - 1;
        if (index >= 0 && index < productos.size()) {
            Producto p = productos.get(index);
            Component conDescuento10 = new Descuento10(p);
            double total = dm.calculateTotal(conDescuento10, p.getPrecio());
            view.mostrarMensaje("Precio con 10% de descuento: $" + total);
        }
    }

    private void aplicarDescuentoPoleras() {
        int index = view.leerNumero("Selecciona el producto (número): ") - 1;
        if (index >= 0 && index < productos.size()) {
            Producto p = productos.get(index);
            Component base = precioBase -> precioBase;
            Component conDescuentoPolera = new DescuentoPoleras20(base, p);
            double total = dm.calculateTotal(conDescuentoPolera, p.getPrecio());
            view.mostrarMensaje("Precio con 20% de descuento para poleras: $" + total);
        }
    }

    private void gestionarCarrito() {
        int cmdOpcion = view.leerNumero("1. Agregar producto\n2. Eliminar producto\nOpción: ");
        int index = view.leerNumero("Selecciona el producto (número): ") - 1;
        if (index >= 0 && index < productos.size()) {
            Producto producto = productos.get(index);
            Command comando = (cmdOpcion == 1) 
                    ? new AgregarProductoCommand(carrito.getProductos(), producto)
                    : new EliminarProductoCommand(carrito.getProductos(), producto);

            invoker.addCommand(comando);
            invoker.ejecutarComandos();

            view.mostrarMensaje("Carrito actual:");
            for (Producto p : carrito.getProductos()) {
                view.mostrarMensaje("- " + p.getNombre() + " $" + p.getPrecio());
            }
        }
    }
}
