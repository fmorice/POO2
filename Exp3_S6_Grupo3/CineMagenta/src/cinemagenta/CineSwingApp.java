/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cinemagenta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Raul
 */
public class CineSwingApp extends JFrame {
    private JTable tablaPeliculas;
    private JLabel lblEstado;
    
    public CineSwingApp() {
        setTitle("Cartelera de Cine - Conexión BD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Crear componentes
        lblEstado = new JLabel("Estado: No conectado");
        lblEstado.setForeground(Color.RED);
        
        JButton btnConectar = new JButton("Conectar a BD");
        btnConectar.addActionListener(e -> conectarBD());
        
        JButton btnMostrar = new JButton("Mostrar Películas");
        btnMostrar.addActionListener(e -> mostrarPeliculas());
        
        // Configurar tabla
        tablaPeliculas = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaPeliculas);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnConectar);
        panelBotones.add(btnMostrar);
        
        // Organizar interfaz
        setLayout(new BorderLayout());
        add(lblEstado, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void conectarBD() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                lblEstado.setText("Estado: Conectado a la base de datos");
                lblEstado.setForeground(Color.GREEN);
                JOptionPane.showMessageDialog(this, "Conexión exitosa!");
            }
        } catch (Exception ex) {
            lblEstado.setText("Estado: Error de conexión");
            lblEstado.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarPeliculas() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Cartelera")) {
            
            // Crear modelo de tabla
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Título");
            model.addColumn("Director");
            model.addColumn("Año");
            model.addColumn("Duración");
            model.addColumn("Género");
            
            // Llenar tabla con datos
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("director"),
                    rs.getInt("anio"),
                    rs.getInt("duracion"),
                    rs.getString("genero")
                });
            }
            
            tablaPeliculas.setModel(model);
            lblEstado.setText("Estado: Datos cargados correctamente");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CineSwingApp().setVisible(true);
        });
    }
}
