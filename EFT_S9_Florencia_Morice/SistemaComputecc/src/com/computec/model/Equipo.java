
package com.computec.model;

public class Equipo {
    private int id;
    private String tipo; // "DESKTOP" o "LAPTOP"
    private String descripcion;
    private String cpu;
    private int discoDuroGB;
    private int ramGB;
    private double precio;
    
    // Campos específicos para Desktop
    private Integer potenciaFuente;
    private String factorForma;
    
    // Campos específicos para Laptop
    private Double tamanoPantalla;
    private Boolean esTouch;
    private Integer puertosUSB;

    // Constructores
    public Equipo() {
    }

    // Constructor para Desktop
    public Equipo(String tipo, String descripcion, String cpu, int discoDuroGB, 
                  int ramGB, double precio, int potenciaFuente, String factorForma) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.cpu = cpu;
        this.discoDuroGB = discoDuroGB;
        this.ramGB = ramGB;
        this.precio = precio;
        this.potenciaFuente = potenciaFuente;
        this.factorForma = factorForma;
        this.tipo = "DESKTOP";
    }

    // Constructor para Laptop
    public Equipo(String tipo, String descripcion, String cpu, int discoDuroGB, 
                  int ramGB, double precio, double tamanoPantalla, 
                  boolean esTouch, int puertosUSB) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.cpu = cpu;
        this.discoDuroGB = discoDuroGB;
        this.ramGB = ramGB;
        this.precio = precio;
        this.tamanoPantalla = tamanoPantalla;
        this.esTouch = esTouch;
        this.puertosUSB = puertosUSB;
        this.tipo = "LAPTOP";
    }

    // Constructor completo
    public Equipo(int id, String tipo, String descripcion, String cpu, 
                  int discoDuroGB, int ramGB, double precio, 
                  Integer potenciaFuente, String factorForma, 
                  Double tamanoPantalla, Boolean esTouch, Integer puertosUSB) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.cpu = cpu;
        this.discoDuroGB = discoDuroGB;
        this.ramGB = ramGB;
        this.precio = precio;
        this.potenciaFuente = potenciaFuente;
        this.factorForma = factorForma;
        this.tamanoPantalla = tamanoPantalla;
        this.esTouch = esTouch;
        this.puertosUSB = puertosUSB;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public int getDiscoDuroGB() {
        return discoDuroGB;
    }

    public void setDiscoDuroGB(int discoDuroGB) {
        this.discoDuroGB = discoDuroGB;
    }

    public int getRamGB() {
        return ramGB;
    }

    public void setRamGB(int ramGB) {
        this.ramGB = ramGB;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Integer getPotenciaFuente() {
        return potenciaFuente;
    }

    public void setPotenciaFuente(Integer potenciaFuente) {
        this.potenciaFuente = potenciaFuente;
    }

    public String getFactorForma() {
        return factorForma;
    }

    public void setFactorForma(String factorForma) {
        this.factorForma = factorForma;
    }

    public Double getTamanoPantalla() {
        return tamanoPantalla;
    }

    public void setTamanoPantalla(Double tamanoPantalla) {
        this.tamanoPantalla = tamanoPantalla;
    }

    public Boolean getEsTouch() {
        return esTouch;
    }

    public void setEsTouch(Boolean esTouch) {
        this.esTouch = esTouch;
    }

    public Integer getPuertosUSB() {
        return puertosUSB;
    }

    public void setPuertosUSB(Integer puertosUSB) {
        this.puertosUSB = puertosUSB;
    }

    // Métodos auxiliares
    public boolean esDesktop() {
        return "DESKTOP".equals(tipo);
    }

    public boolean esLaptop() {
        return "LAPTOP".equals(tipo);
    }

    public String getEsTouchTexto() {
        return esTouch != null && esTouch ? "Sí" : "No";
    }

    // Método toString para mostrar información
    @Override
    public String toString() {
        return descripcion + " - $" + precio;
    }

    // Método para obtener detalles específicos según el tipo
    public String getDetallesEspecificos() {
        if (esDesktop()) {
            return "Fuente: " + potenciaFuente + "W, Factor: " + factorForma;
        } else if (esLaptop()) {
            return "Pantalla: " + tamanoPantalla + "\", Touch: " + getEsTouchTexto() + 
                   ", USB: " + puertosUSB + " puertos";
        }
        return "";
    }
}