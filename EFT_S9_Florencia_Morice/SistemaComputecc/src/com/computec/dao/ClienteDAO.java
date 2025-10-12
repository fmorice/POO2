package com.computec.dao;

import com.computec.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class ClienteDAO {
    
    public boolean guardar(Cliente cliente) {
        String sql = "INSERT INTO clientes (rut, nombre_completo, direccion, comuna, email, telefono) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, cliente.getRut());
            pstmt.setString(2, cliente.getNombreCompleto());
            pstmt.setString(3, cliente.getDireccion());
            pstmt.setString(4, cliente.getComuna());
            pstmt.setString(5, cliente.getEmail());
            pstmt.setString(6, cliente.getTelefono());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                // Obtener el ID generado
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cliente.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al guardar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY nombre_completo";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setRut(rs.getString("rut"));
                cliente.setNombreCompleto(rs.getString("nombre_completo"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setComuna(rs.getString("comuna"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefono(rs.getString("telefono"));
                
                clientes.add(cliente);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return clientes;
    }
    
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setRut(rs.getString("rut"));
                cliente.setNombreCompleto(rs.getString("nombre_completo"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setComuna(rs.getString("comuna"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefono(rs.getString("telefono"));
                
                return cliente;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public Cliente buscarPorRut(String rut) {
        String sql = "SELECT * FROM clientes WHERE rut = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, rut);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setRut(rs.getString("rut"));
                cliente.setNombreCompleto(rs.getString("nombre_completo"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setComuna(rs.getString("comuna"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefono(rs.getString("telefono"));
                
                return cliente;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por RUT: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET rut = ?, nombre_completo = ?, direccion = ?, comuna = ?, email = ?, telefono = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cliente.getRut());
            pstmt.setString(2, cliente.getNombreCompleto());
            pstmt.setString(3, cliente.getDireccion());
            pstmt.setString(4, cliente.getComuna());
            pstmt.setString(5, cliente.getEmail());
            pstmt.setString(6, cliente.getTelefono());
            pstmt.setInt(7, cliente.getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}