package com.tienda.discount;

import com.tienda.model.Product;

/**
 * DiscountManager:
 * Clase Singleton que aplica descuentos a los productos.
 * Esta versión usa if/else para decidir los descuentos.
 *
 * Patrón Singleton:
 *  - Constructor privado para impedir instanciación externa.
 *  - Método estático getInstance() devuelve siempre la misma instancia única.
 */
public final class DiscountManager {

    // Instancia única del Singleton
    private static DiscountManager instance;

    // Constructor privado
    private DiscountManager() { }

    /**
     * Devuelve la única instancia de DiscountManager.
     * Si no existe aún, la crea en este momento.
     */
    public static DiscountManager getInstance() {
        if (instance == null) {
            instance = new DiscountManager();
        }
        return instance;
    }

    /**
     * Calcula el precio final con descuentos aplicados según reglas fijas:
     *  - Si la categoría es "poleras": 15% de descuento.
     *  - Si la categoría es "accesorios": 2x1.
     *  - Si el cupón es "PROMO10": 10% adicional.
     *
     * @param product producto a comprar
     * @param quantity cantidad de unidades
     * @param couponCode código de cupón o null si no hay
     * @return precio final con descuentos
     */
    public double calculateTotal(Product product, int quantity, String couponCode) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }

        double price = product.getPrice();
        String category = product.getCategory().toLowerCase();

        // Regla 2x1 para accesorios
        int unitsToCharge = quantity;
        if (category.equals("accesorios")) {
            unitsToCharge = quantity - (quantity / 2);
        }

        // Precio base considerando las unidades a cobrar
        double total = price * unitsToCharge;

        // 15% de descuento en poleras
        if (category.equals("poleras")) {
            total *= 0.85; // paga solo el 85%
        }

        // Cupón PROMO10 -> 10% de descuento extra
        if (couponCode != null && couponCode.equalsIgnoreCase("PROMO10")) {
            total *= 0.90; // paga solo el 90%
        }

        return total;
    }
}
