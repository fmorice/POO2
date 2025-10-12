
package com.computec.model;

import java.util.Date;

public class Venta {
    private int id;
    private int clienteId;
    private int equipoId;
    private Date fechaVenta;
    
    // Objetos relacionados (para mostrar información completa)
    private Cliente cliente;
    private Equipo equipo;

    // Constructores
    public Venta() {
        this.fechaVenta = new Date(); // Fecha actual por defecto
    }

    public Venta(int clienteId, int equipoId) {
        this.clienteId = clienteId;
        this.equipoId = equipoId;
        this.fechaVenta = new Date();
    }

    public Venta(int clienteId, int equipoId, Date fechaVenta) {
        this.clienteId = clienteId;
        this.equipoId = equipoId;
        this.fechaVenta = fechaVenta;
    }

    public Venta(int id, int clienteId, int equipoId, Date fechaVenta) {
        this.id = id;
        this.clienteId = clienteId;
        this.equipoId = equipoId;
        this.fechaVenta = fechaVenta;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(int equipoId) {
        this.equipoId = equipoId;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    // Métodos auxiliares
    public double getMontoTotal() {
        return equipo != null ? equipo.getPrecio() : 0.0;
    }

    public String getNombreCliente() {
        return cliente != null ? cliente.getNombreCompleto() : "Cliente no disponible";
    }

    public String getDescripcionEquipo() {
        return equipo != null ? equipo.getDescripcion() : "Equipo no disponible";
    }

    public String getTipoEquipo() {
        return equipo != null ? equipo.getTipo() : "Tipo no disponible";
    }

    // Método toString para mostrar información
    @Override
    public String toString() {
        return "Venta #" + id + " - " + getNombreCliente() + " - " + getDescripcionEquipo() + 
               " - $" + getMontoTotal();
    }

    // Método para validar que la venta tenga todos los datos necesarios
    public boolean esValida() {
        return clienteId > 0 && equipoId > 0 && fechaVenta != null;
    }
}