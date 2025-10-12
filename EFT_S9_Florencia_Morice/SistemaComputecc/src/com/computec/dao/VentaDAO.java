package com.computec.dao;

import com.computec.model.Cliente;
import com.computec.model.Equipo;
import com.computec.model.Venta;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {
    
    public boolean guardar(Venta venta) {
        String sql = "INSERT INTO ventas (cliente_id, equipo_id, fecha_venta) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, venta.getClienteId());
            pstmt.setInt(2, venta.getEquipoId());
            pstmt.setTimestamp(3, new Timestamp(venta.getFechaVenta().getTime()));
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        venta.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al guardar venta: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Venta> obtenerTodas() {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT v.*, c.rut, c.nombre_completo, c.email, c.telefono, " +
                    "e.descripcion, e.tipo, e.precio " +
                    "FROM ventas v " +
                    "JOIN clientes c ON v.cliente_id = c.id " +
                    "JOIN equipos e ON v.equipo_id = e.id " +
                    "ORDER BY v.fecha_venta DESC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Venta venta = mapearVentaDesdeResultSet(rs);
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ventas;
    }
    
    public List<Venta> obtenerPorTipoEquipo(String tipo) {
        List<Venta> ventas = new ArrayList<>();
        String sql = "SELECT v.*, c.rut, c.nombre_completo, c.email, c.telefono, " +
                    "e.descripcion, e.tipo, e.precio " +
                    "FROM ventas v " +
                    "JOIN clientes c ON v.cliente_id = c.id " +
                    "JOIN equipos e ON v.equipo_id = e.id " +
                    "WHERE e.tipo = ? " +
                    "ORDER BY v.fecha_venta DESC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tipo);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Venta venta = mapearVentaDesdeResultSet(rs);
                ventas.add(venta);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener ventas por tipo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return ventas;
    }
    
    private Venta mapearVentaDesdeResultSet(ResultSet rs) throws SQLException {
        Venta venta = new Venta();
        venta.setId(rs.getInt("id"));
        venta.setClienteId(rs.getInt("cliente_id"));
        venta.setEquipoId(rs.getInt("equipo_id"));
        venta.setFechaVenta(rs.getTimestamp("fecha_venta"));
        
        // Crear y configurar cliente
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("cliente_id"));
        cliente.setRut(rs.getString("rut"));
        cliente.setNombreCompleto(rs.getString("nombre_completo"));
        cliente.setEmail(rs.getString("email"));
        cliente.setTelefono(rs.getString("telefono"));
        venta.setCliente(cliente);
        
        // Crear y configurar equipo
        Equipo equipo = new Equipo();
        equipo.setId(rs.getInt("equipo_id"));
        equipo.setDescripcion(rs.getString("descripcion"));
        equipo.setTipo(rs.getString("tipo"));
        equipo.setPrecio(rs.getDouble("precio"));
        venta.setEquipo(equipo);
        
        return venta;
    }
    
    public Venta buscarPorId(int id) {
        String sql = "SELECT v.*, c.rut, c.nombre_completo, c.email, c.telefono, " +
                    "e.descripcion, e.tipo, e.precio " +
                    "FROM ventas v " +
                    "JOIN clientes c ON v.cliente_id = c.id " +
                    "JOIN equipos e ON v.equipo_id = e.id " +
                    "WHERE v.id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapearVentaDesdeResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar venta por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public double obtenerMontoTotalVentas() {
        String sql = "SELECT SUM(e.precio) as total FROM ventas v JOIN equipos e ON v.equipo_id = e.id";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener monto total: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0.0;
    }
    
    public int obtenerCantidadTotalVentas() {
        String sql = "SELECT COUNT(*) as total FROM ventas";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener cantidad total: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
}