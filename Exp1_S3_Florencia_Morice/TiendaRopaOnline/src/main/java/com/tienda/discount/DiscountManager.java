package com.tienda.discount;

import java.math.BigDecimal;

public class DiscountManager {
    private static DiscountManager instance;
    
    private DiscountManager() {}
    
    public static synchronized DiscountManager getInstance() {
        if (instance == null) {
            instance = new DiscountManager();
        }
        return instance;
    }
    
    public BigDecimal calculateTotal(Component component, BigDecimal precioBase) {
        return component.aplicarDescuento(precioBase);
    }
}