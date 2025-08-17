package com.tienda.core;

import com.tienda.model.Product;

/**
 Singleton: garantiza que una clase tenga solo una instancia
 y proporciona un punto global de acceso a ella.
 
 * En este caso, usamos un constructor privado para impedir que
 * otras clases creen nuevas instancias con "new".
 * El método estático getInstance() devuelve siempre la misma
 * instancia única, creándola solo la primera vez que se llama.
 *
 * Beneficio: se asegura que todos los componentes de la aplicación
 * usen siempre el mismo objeto compartido.
 */
public final class Singleton {

    // Instancia única (privada y estática)
    private static Singleton instance;

    // Constructor privado: evita la creación directa con "new"
    private Singleton() { }

    /**
     * Devuelve la única instancia de la clase.
     * Si no existe aún, la crea.
     *
     * @return instancia única de Singleton
     */
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    // Ejemplo de atributo compartido
    private String appName = "Tienda de Ropa Online";

    // Métodos getter y setter
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
