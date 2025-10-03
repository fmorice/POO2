package cinemagenta;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CineSwingApp extends JFrame {
    private JTable tablaPeliculas;
    private JLabel lblEstado;
    
    // Componentes para filtros
    private JComboBox<String> cmbGenero;
    private JTextField txtAnioMin;
    private JTextField txtAnioMax;
    private JButton btnFiltrar;
    private JButton btnLimpiarFiltros;
    
    public CineSwingApp() {
        setTitle("Cartelera de Cine - Cine Magenta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); // ✅ Aumentado para los filtros
        setLocationRelativeTo(null);
        
        // Crear componentes principales
        lblEstado = new JLabel("Estado: No conectado");
        lblEstado.setForeground(Color.RED);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton btnConectar = new JButton("Conectar a BD");
        JButton btnMostrar = new JButton("Mostrar Todas las Películas");
        JButton btnGestion = new JButton("Gestión de Películas");
        
        // Crear componentes de filtros
        crearComponentesFiltros();
        
        // Configurar acciones de botones
        btnConectar.addActionListener(e -> conectarBD());
        btnMostrar.addActionListener(e -> mostrarTodasPeliculas());
        btnGestion.addActionListener(e -> abrirGestionPeliculas());
        btnFiltrar.addActionListener(e -> filtrarPeliculas());
        btnLimpiarFiltros.addActionListener(e -> limpiarFiltros());
        
        // Configurar tabla
        tablaPeliculas = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaPeliculas);
        
        // Panel de filtros
        JPanel panelFiltros = crearPanelFiltros();
        
        // Panel de botones principales
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
        add(panelFiltros, BorderLayout.WEST); // ✅ Filtros a la izquierda
        add(panelBotones, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
        
        //  Cargar géneros disponibles al iniciar
        cargarGeneros();
    }
    
    // Método para crear componentes de filtros
    private void crearComponentesFiltros() {
        cmbGenero = new JComboBox<>();
        cmbGenero.addItem("Todos los géneros"); // Opción por defecto
        
        txtAnioMin = new JTextField(4);
        txtAnioMin.setToolTipText("Año mínimo (ej: 1980)");
        
        txtAnioMax = new JTextField(4);
        txtAnioMax.setToolTipText("Año máximo (ej: 2023)");
        
        btnFiltrar = new JButton("Aplicar Filtros");
        btnFiltrar.setBackground(new Color(70, 130, 180));
        btnFiltrar.setForeground(Color.WHITE);
        
        btnLimpiarFiltros = new JButton("Limpiar Filtros");
        btnLimpiarFiltros.setBackground(new Color(169, 169, 169));
        btnLimpiarFiltros.setForeground(Color.WHITE);
    }
    
    // Método para crear el panel de filtros
    private JPanel crearPanelFiltros() {
        JPanel panelFiltros = new JPanel();
        panelFiltros.setLayout(new BoxLayout(panelFiltros, BoxLayout.Y_AXIS));
        panelFiltros.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), 
            "Filtros de Búsqueda",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12),
            Color.BLUE
        ));
        panelFiltros.setPreferredSize(new Dimension(200, 300));
        
        // Filtro por género
        panelFiltros.add(new JLabel("Género:"));
        panelFiltros.add(cmbGenero);
        panelFiltros.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Filtro por rango de años
        panelFiltros.add(new JLabel("Año Mínimo:"));
        panelFiltros.add(txtAnioMin);
        panelFiltros.add(Box.createRigidArea(new Dimension(0, 5)));
        
        panelFiltros.add(new JLabel("Año Máximo:"));
        panelFiltros.add(txtAnioMax);
        panelFiltros.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Botones de filtros
        panelFiltros.add(btnFiltrar);
        panelFiltros.add(Box.createRigidArea(new Dimension(0, 5)));
        panelFiltros.add(btnLimpiarFiltros);
        
        return panelFiltros;
    }
    
    //  Cargar géneros disponibles desde la BD
    private void cargarGeneros() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT genero FROM peliculas ORDER BY genero")) {
            
            while (rs.next()) {
                cmbGenero.addItem(rs.getString("genero"));
            }
            
        } catch (Exception ex) {
            System.err.println("Error al cargar géneros: " + ex.getMessage());
        }
    }
    
    private void conectarBD() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                lblEstado.setText("Estado: Conectado a la base de datos - Cine Magenta");
                lblEstado.setForeground(Color.GREEN);
                JOptionPane.showMessageDialog(this, "Conexión exitosa a la base de datos!");
                cargarGeneros(); // Recargar géneros después de conectar
            }
        } catch (Exception ex) {
            lblEstado.setText("Estado: Error de conexión");
            lblEstado.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //  Mostrar todas las películas (sin filtros)
    private void mostrarTodasPeliculas() {
        cargarPeliculas("SELECT * FROM peliculas ORDER BY anio DESC");
    }
    
    //  Método para filtrar películas
    private void filtrarPeliculas() {
        StringBuilder sql = new StringBuilder("SELECT * FROM peliculas WHERE 1=1");
        List<Object> parametros = new ArrayList<>();
        
        // Filtro por género
        String generoSeleccionado = (String) cmbGenero.getSelectedItem();
        if (generoSeleccionado != null && !generoSeleccionado.equals("Todos los géneros")) {
            sql.append(" AND genero = ?");
            parametros.add(generoSeleccionado);
        }
        
        // Filtro por rango de años
        if (!txtAnioMin.getText().trim().isEmpty()) {
            try {
                int anioMin = Integer.parseInt(txtAnioMin.getText().trim());
                sql.append(" AND anio >= ?");
                parametros.add(anioMin);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Año mínimo debe ser un número válido", 
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        if (!txtAnioMax.getText().trim().isEmpty()) {
            try {
                int anioMax = Integer.parseInt(txtAnioMax.getText().trim());
                sql.append(" AND anio <= ?");
                parametros.add(anioMax);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Año máximo debe ser un número válido", 
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        sql.append(" ORDER BY anio DESC");
        cargarPeliculasConParametros(sql.toString(), parametros.toArray());
    }
    
    //  Método genérico para cargar películas con parámetros
    private void cargarPeliculasConParametros(String sql, Object[] parametros) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Establecer parámetros
            for (int i = 0; i < parametros.length; i++) {
                pstmt.setObject(i + 1, parametros[i]);
            }
            
            ResultSet rs = pstmt.executeQuery();
            llenarTabla(rs);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos filtrados: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //  Método genérico para cargar películas
    private void cargarPeliculas(String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            llenarTabla(rs);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar datos: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para llenar la tabla (reutilizable)
    private void llenarTabla(ResultSet rs) throws Exception {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Título");
        model.addColumn("Director");
        model.addColumn("Año");
        model.addColumn("Duración");
        model.addColumn("Género");
        
        int contador = 0;
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("director"),
                rs.getInt("anio"),
                rs.getInt("duracion"),
                rs.getString("genero")
            });
            contador++;
        }
        
        tablaPeliculas.setModel(model);
        lblEstado.setText("Estado: " + contador + " películas encontradas");
    }
    
    //  Limpiar filtros
    private void limpiarFiltros() {
        cmbGenero.setSelectedIndex(0); // "Todos los géneros"
        txtAnioMin.setText("");
        txtAnioMax.setText("");
        mostrarTodasPeliculas(); // Mostrar todas las películas
    }
    
    private void abrirGestionPeliculas() {
        SwingUtilities.invokeLater(() -> {
            SistemaGestionPeliculas gestion = new SistemaGestionPeliculas();
            gestion.setVisible(true);
        });
    }
}