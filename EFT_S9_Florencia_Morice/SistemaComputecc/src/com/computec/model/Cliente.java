
package com.computec.model;

public class Cliente {
    private int id;
    private String rut;
    private String nombreCompleto;
    private String direccion;
    private String comuna;
    private String email;
    private String telefono;

    // Constructores
    public Cliente() {
    }

    public Cliente(String rut, String nombreCompleto, String direccion, 
                   String comuna, String email, String telefono) {
        this.rut = rut;
        this.nombreCompleto = nombreCompleto;
        this.direccion = direccion;
        this.comuna = comuna;
        this.email = email;
        this.telefono = telefono;
    }

    public Cliente(int id, String rut, String nombreCompleto, String direccion, 
                   String comuna, String email, String telefono) {
        this.id = id;
        this.rut = rut;
        this.nombreCompleto = nombreCompleto;
        this.direccion = direccion;
        this.comuna = comuna;
        this.email = email;
        this.telefono = telefono;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Método toString para mostrar información
    @Override
    public String toString() {
        return rut + " - " + nombreCompleto;
    }

    // Método para validar RUT (opcional)
    public boolean validarRut() {
        if (rut == null || rut.isEmpty()) {
            return false;
        }
        // Aquí puedes agregar lógica de validación de RUT chileno
        return rut.matches("\\d{1,8}-[\\dkK]");
    }
}