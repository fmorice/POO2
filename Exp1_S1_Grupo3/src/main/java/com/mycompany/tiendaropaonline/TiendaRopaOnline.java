package com.mycompany.tiendaropaonline;

import com.tienda.discount.DiscountManager;
import com.tienda.model.Product;

public class TiendaRopaOnline {
    public static void main(String[] args) {
        // Obtengo la única instancia del DiscountManager
        DiscountManager dm = DiscountManager.getInstance();

        // Creo productos
        Product polera = new Product("Polera Básica", "poleras", 10000);
        Product cinturon = new Product("Cinturón Cuero", "accesorios", 8000);

        // Calculo totales
        double totalPoleras = dm.calculateTotal(polera, 3, "PROMO10");
        double totalCinturones = dm.calculateTotal(cinturon, 3, null);

        // Muestro resultados
        System.out.println("Total poleras (3 uds, 15% cat, 10% cupón): " + totalPoleras);
        System.out.println("Total cinturones (3 uds, 2x1): " + totalCinturones);
    }
}
