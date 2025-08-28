package com.tienda.discount;

/**
 * DiscountManager:
 * Clase Singleton que aplica descuentos a los productos usando el patrón Decorator.
 * Mantiene una única instancia para toda la aplicación.
 */

public final class DiscountManager {

    // Instancia única y final del Singleton
    private static final DiscountManager instance = new DiscountManager();

    // Constructor privado para impedir instanciación externa
    private DiscountManager() { }

    /**
     * Devuelve la única instancia de DiscountManager
     */
    public static DiscountManager getInstance() {
        return instance;
    }

    /**
     * Calcula el precio final con el descuento aplicado por el componente
     * @param component Producto o decorador que implementa Component
     * @param precioBase Precio base del producto
     * @return precio final con descuento aplicado
     */
    public double calculateTotal(Component component, double precioBase) {
        if (component == null) {
            throw new IllegalArgumentException("El componente no puede ser nulo");
        }
        if (precioBase < 0) {
            throw new IllegalArgumentException("El precio base no puede ser negativo");
        }

        // Llama al método aplicarDescuento del componente (puede ser decorador)
        return component.aplicarDescuento(precioBase);
    }
}
