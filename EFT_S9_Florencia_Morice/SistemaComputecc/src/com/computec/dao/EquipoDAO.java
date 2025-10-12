package com.computec.dao;

import com.computec.model.Cliente;
import com.computec.model.Equipo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {
    
    public boolean guardar(Equipo equipo) {
        String sql = "INSERT INTO equipos (tipo, descripcion, cpu, disco_duro_gb, ram_gb, precio, " +
                    "potencia_fuente, factor_forma, tamano_pantalla, es_touch, puertos_usb) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, equipo.getTipo());
            pstmt.setString(2, equipo.getDescripcion());
            pstmt.setString(3, equipo.getCpu());
            pstmt.setInt(4, equipo.getDiscoDuroGB());
            pstmt.setInt(5, equipo.getRamGB());
            pstmt.setDouble(6, equipo.getPrecio());
            
            // Campos específicos según el tipo
            if (equipo.esDesktop()) {
                pstmt.setInt(7, equipo.getPotenciaFuente());
                pstmt.setString(8, equipo.getFactorForma());
                pstmt.setNull(9, Types.DOUBLE);
                pstmt.setNull(10, Types.BOOLEAN);
                pstmt.setNull(11, Types.INTEGER);
            } else if (equipo.esLaptop()) {
                pstmt.setNull(7, Types.INTEGER);
                pstmt.setNull(8, Types.VARCHAR);
                pstmt.setDouble(9, equipo.getTamanoPantalla());
                pstmt.setBoolean(10, equipo.getEsTouch());
                pstmt.setInt(11, equipo.getPuertosUSB());
            } else {
                // Para tipos no especificados, setear todos como null
                pstmt.setNull(7, Types.INTEGER);
                pstmt.setNull(8, Types.VARCHAR);
                pstmt.setNull(9, Types.DOUBLE);
                pstmt.setNull(10, Types.BOOLEAN);
                pstmt.setNull(11, Types.INTEGER);
            }
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        equipo.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al guardar equipo: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Equipo> obtenerTodos() {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT * FROM equipos ORDER BY descripcion";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Equipo equipo = mapearEquipoDesdeResultSet(rs);
                equipos.add(equipo);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener equipos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return equipos;
    }
    
    public List<Equipo> obtenerPorTipo(String tipo) {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT * FROM equipos WHERE tipo = ? ORDER BY descripcion";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tipo);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Equipo equipo = mapearEquipoDesdeResultSet(rs);
                equipos.add(equipo);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener equipos por tipo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return equipos;
    }
    
    public Equipo buscarPorId(int id) {
        String sql = "SELECT * FROM equipos WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapearEquipoDesdeResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar equipo por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    private Equipo mapearEquipoDesdeResultSet(ResultSet rs) throws SQLException {
        Equipo equipo = new Equipo();
        equipo.setId(rs.getInt("id"));
        equipo.setTipo(rs.getString("tipo"));
        equipo.setDescripcion(rs.getString("descripcion"));
        equipo.setCpu(rs.getString("cpu"));
        equipo.setDiscoDuroGB(rs.getInt("disco_duro_gb"));
        equipo.setRamGB(rs.getInt("ram_gb"));
        equipo.setPrecio(rs.getDouble("precio"));
        
        // Campos específicos
        equipo.setPotenciaFuente(rs.getInt("potencia_fuente"));
        if (rs.wasNull()) equipo.setPotenciaFuente(null);
        
        equipo.setFactorForma(rs.getString("factor_forma"));
        
        equipo.setTamanoPantalla(rs.getDouble("tamano_pantalla"));
        if (rs.wasNull()) equipo.setTamanoPantalla(null);
        
        equipo.setEsTouch(rs.getBoolean("es_touch"));
        if (rs.wasNull()) equipo.setEsTouch(null);
        
        equipo.setPuertosUSB(rs.getInt("puertos_usb"));
        if (rs.wasNull()) equipo.setPuertosUSB(null);
        
        return equipo;
    }
    
    public boolean actualizar(Equipo equipo) {
        String sql = "UPDATE equipos SET tipo = ?, descripcion = ?, cpu = ?, disco_duro_gb = ?, " +
                    "ram_gb = ?, precio = ?, potencia_fuente = ?, factor_forma = ?, " +
                    "tamano_pantalla = ?, es_touch = ?, puertos_usb = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, equipo.getTipo());
            pstmt.setString(2, equipo.getDescripcion());
            pstmt.setString(3, equipo.getCpu());
            pstmt.setInt(4, equipo.getDiscoDuroGB());
            pstmt.setInt(5, equipo.getRamGB());
            pstmt.setDouble(6, equipo.getPrecio());
            
            // Campos específicos según el tipo
            if (equipo.esDesktop()) {
                pstmt.setInt(7, equipo.getPotenciaFuente());
                pstmt.setString(8, equipo.getFactorForma());
                pstmt.setNull(9, Types.DOUBLE);
                pstmt.setNull(10, Types.BOOLEAN);
                pstmt.setNull(11, Types.INTEGER);
            } else if (equipo.esLaptop()) {
                pstmt.setNull(7, Types.INTEGER);
                pstmt.setNull(8, Types.VARCHAR);
                pstmt.setDouble(9, equipo.getTamanoPantalla());
                pstmt.setBoolean(10, equipo.getEsTouch());
                pstmt.setInt(11, equipo.getPuertosUSB());
            }
            
            pstmt.setInt(12, equipo.getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar equipo: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM equipos WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar equipo: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}