package com.tienda.discount;

import java.math.BigDecimal;

public interface Component {
    BigDecimal aplicarDescuento(BigDecimal precioBase);
}