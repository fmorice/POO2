package cinemagenta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CineSwingApp extends JFrame {
    private JTable tablaPeliculas;
    private JLabel lblEstado;
    
    public CineSwingApp() {
        setTitle("Cartelera de Cine - Cine Magenta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        // Crear componentes
        lblEstado = new JLabel("Estado: No conectado");
        lblEstado.setForeground(Color.RED);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton btnConectar = new JButton("Conectar a BD");
        JButton btnMostrar = new JButton("Mostrar Películas");
        JButton btnGestion = new JButton("Gestión de Películas");
        
        // Configurar acciones de botones
        btnConectar.addActionListener(e -> conectarBD());
        btnMostrar.addActionListener(e -> mostrarPeliculas());
        btnGestion.addActionListener(e -> abrirGestionPeliculas());
        
        // Configurar tabla
        tablaPeliculas = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaPeliculas);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnConectar);
        panelBotones.add(btnMostrar);
        panelBotones.add(btnGestion);
        
        // Organizar interfaz
        setLayout(new BorderLayout(10, 10));
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(lblEstado, BorderLayout.CENTER);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void conectarBD() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                lblEstado.setText("Estado: Conectado a la base de datos - Cine Magenta");
                lblEstado.setForeground(Color.GREEN);
                JOptionPane.showMessageDialog(this, "Conexión exitosa a la base de datos!");
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
             ResultSet rs = stmt.executeQuery("SELECT * FROM peliculas")) {
            
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
            lblEstado.setText("Estado: " + model.getRowCount() + " películas cargadas");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirGestionPeliculas() {
        SwingUtilities.invokeLater(() -> {
            SistemaGestionPeliculas gestion = new SistemaGestionPeliculas();
            gestion.setVisible(true);
        });
    }
}