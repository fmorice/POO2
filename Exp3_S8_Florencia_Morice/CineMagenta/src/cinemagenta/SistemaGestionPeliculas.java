package cinemagenta;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Sistema de Gestión de Películas con controles de entrada mejorados
 */
public class SistemaGestionPeliculas extends JFrame {
    
    // COMPONENTES MEJORADOS DE LA INTERFAZ GRÁFICA
    private JFormattedTextField txtId;        // Para ID (numérico)
    private JTextField txtTitulo;    
    private JTextField txtDirector;  
    private JSpinner spinnerAnio;             // Spinner para año
    private JSpinner spinnerDuracion;         // Spinner para duración
    private JTextField txtGenero;    
    private JSpinner spinnerPrecio;           // Spinner para precio
    
    // Botones
    private JButton btnBuscar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnAgregar;
    
    // CONFIGURACIÓN
    private static final String TABLA = "peliculas";
    
    public SistemaGestionPeliculas() {
        inicializarInterfaz();
        configurarEventos();
    }
    
    private void inicializarInterfaz() {
        setTitle("Sistema de Gestión de Películas - Cine Magenta (Mejorado)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 450);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel del formulario con controles mejorados
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLUE), 
            "Datos de la Película - Controles Mejorados", 
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12), Color.BLUE
        ));
        
      
        // CONTROLES MEJORADOS PARA TIPOS DE DATO ESPECÍFICOS

        
        // Campo para ID (JFormattedTextField numérico)
        panelFormulario.add(new JLabel("ID Película:"));
        txtId = crearCampoNumerico(1, 9999);
        txtId.setToolTipText("Ingrese el ID numérico de la película (1-9999)");
        panelFormulario.add(txtId);
        
        // Campo para título
        panelFormulario.add(new JLabel("Título:*"));
        txtTitulo = new JTextField();
        txtTitulo.setToolTipText("Ingrese el título de la película");
        panelFormulario.add(txtTitulo);
        
        // Campo para director
        panelFormulario.add(new JLabel("Director:*"));
        txtDirector = new JTextField();
        txtDirector.setToolTipText("Ingrese el nombre del director");
        panelFormulario.add(txtDirector);
        
        // Spinner para año (1900-2023)
        panelFormulario.add(new JLabel("Año:*"));
        spinnerAnio = new JSpinner(new SpinnerNumberModel(2023, 1900, 2023, 1));
        spinnerAnio.setToolTipText("Seleccione el año de estreno (1900-2023)");
        panelFormulario.add(spinnerAnio);
        
        // Spinner para duración (1-300 minutos)
        panelFormulario.add(new JLabel("Duración (min):*"));
        spinnerDuracion = new JSpinner(new SpinnerNumberModel(120, 1, 300, 1));
        spinnerDuracion.setToolTipText("Duración en minutos (1-300)");
        panelFormulario.add(spinnerDuracion);
        
        // Campo para género
        panelFormulario.add(new JLabel("Género:*"));
        txtGenero = new JTextField();
        txtGenero.setToolTipText("Ingrese el género cinematográfico");
        panelFormulario.add(txtGenero);
        
        // Spinner para precio (0-50000)
        panelFormulario.add(new JLabel("Precio:*"));
        spinnerPrecio = new JSpinner(new SpinnerNumberModel(5000.0, 0.0, 50000.0, 100.0));
        JSpinner.NumberEditor precioEditor = new JSpinner.NumberEditor(spinnerPrecio, "$ #,##0.00");
        spinnerPrecio.setEditor(precioEditor);
        spinnerPrecio.setToolTipText("Precio de entrada ($0 - $50,000)");
        panelFormulario.add(spinnerPrecio);
        
     
        // PANEL DE BOTONES MEJORADO
      
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        btnBuscar = crearBoton("Buscar Película", new Color(70, 130, 180), 
                              "Buscar una película por su ID");
        btnAgregar = crearBoton("Agregar Nueva", new Color(34, 139, 34), 
                               "Agregar una nueva película a la base de datos");
        btnModificar = crearBoton("Modificar", new Color(255, 140, 0), 
                                 "Guardar cambios en la película actual");
        btnEliminar = crearBoton("Eliminar", new Color(220, 20, 60), 
                                "Eliminar permanentemente la película");
        btnLimpiar = crearBoton("Limpiar", new Color(128, 128, 128), 
                               "Limpiar todos los campos del formulario");
        
        panelBotones.add(btnBuscar);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
 
        // ENSAMBLAJE FINAL

        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }
    
    /**
     * Crea un JFormattedTextField configurado para números
     */
    private JFormattedTextField crearCampoNumerico(int min, int max) {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setMinimum(min);
        formatter.setMaximum(max);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        
        return new JFormattedTextField(formatter);
    }
    
    /**
     * Crea un botón con estilo consistente
     */
    private JButton crearBoton(String texto, Color colorFondo, String tooltip) {
        JButton boton = new JButton(texto);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setToolTipText(tooltip);
        return boton;
    }
    
    private void configurarEventos() {
        btnBuscar.addActionListener(e -> buscarPelicula());
        btnAgregar.addActionListener(e -> agregarPelicula());
        btnModificar.addActionListener(e -> modificarPelicula());
        btnEliminar.addActionListener(e -> eliminarPelicula());
        btnLimpiar.addActionListener(e -> limpiarFormularioCompleto());
        
        // Enter en el campo ID ejecuta búsqueda
        txtId.addActionListener(e -> buscarPelicula());
        
        // Enter en título mueve al siguiente campo
        txtTitulo.addActionListener(e -> txtDirector.requestFocus());
    }
    
   
    // MÉTODOS PRINCIPALES MEJORADOS
   
    
    private void buscarPelicula() {
        try {
            String idText = txtId.getText().trim();
            
            if (idText.isEmpty()) {
                mostrarMensaje("Por favor, ingrese un ID de película para buscar.", 
                              "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int id = Integer.parseInt(idText);
            
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM " + TABLA + " WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                
                ResultSet rs = pstmt.executeQuery();
                
                if (rs.next()) {
                    // Cargar datos en los controles mejorados
                    txtTitulo.setText(rs.getString("titulo"));
                    txtDirector.setText(rs.getString("director"));
                    spinnerAnio.setValue(rs.getInt("anio"));
                    spinnerDuracion.setValue(rs.getInt("duracion"));
                    txtGenero.setText(rs.getString("genero"));
                    
                    // Manejar precio si existe la columna
                    try {
                        double precio = rs.getDouble("precio");
                        spinnerPrecio.setValue(precio);
                    } catch (SQLException e) {
                        // Si no existe la columna precio, usar valor por defecto
                        spinnerPrecio.setValue(5000.0);
                    }
                    
                    mostrarMensaje("Película encontrada correctamente.", 
                                  "Búsqueda Exitosa", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    mostrarMensaje("No se encontró ninguna película con el ID: " + id, 
                                  "Película No Encontrada", JOptionPane.ERROR_MESSAGE);
                    limpiarFormulario();
                }
                
            } catch (SQLException ex) {
                mostrarMensaje("Error al buscar la película: " + ex.getMessage(), 
                              "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException ex) {
            mostrarMensaje("El ID debe ser un número válido entre 1 y 9999.", 
                          "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void agregarPelicula() {
        if (!validarCampos()) {
            return;
        }
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Verificar si la columna precio existe
            boolean tienePrecio = verificarColumnaPrecio(conn);
            
            String sql;
            if (tienePrecio) {
                sql = "INSERT INTO " + TABLA + " (titulo, director, anio, duracion, genero, precio) VALUES (?, ?, ?, ?, ?, ?)";
            } else {
                sql = "INSERT INTO " + TABLA + " (titulo, director, anio, duracion, genero) VALUES (?, ?, ?, ?, ?)";
            }
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, txtTitulo.getText().trim());
            pstmt.setString(2, txtDirector.getText().trim());
            pstmt.setInt(3, (Integer) spinnerAnio.getValue());
            pstmt.setInt(4, (Integer) spinnerDuracion.getValue());
            pstmt.setString(5, txtGenero.getText().trim());
            
            if (tienePrecio) {
                pstmt.setDouble(6, (Double) spinnerPrecio.getValue());
            }
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                mostrarMensaje("Película agregada correctamente con ID: " + obtenerUltimoId(conn),
                              "Agregado Exitoso", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            }
            
        } catch (SQLException ex) {
            mostrarMensaje("Error al agregar película: " + ex.getMessage(),
                          "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarPelicula() {
        if (!validarCampos()) {
            return;
        }
        
        String idText = txtId.getText().trim();
        if (idText.isEmpty()) {
            mostrarMensaje("Para modificar, primero busque una película por ID.",
                          "ID Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea modificar esta película?",
            "Confirmar Modificación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            boolean tienePrecio = verificarColumnaPrecio(conn);
            
            String sql;
            if (tienePrecio) {
                sql = "UPDATE " + TABLA + " SET titulo=?, director=?, anio=?, duracion=?, genero=?, precio=? WHERE id=?";
            } else {
                sql = "UPDATE " + TABLA + " SET titulo=?, director=?, anio=?, duracion=?, genero=? WHERE id=?";
            }
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, txtTitulo.getText().trim());
            pstmt.setString(2, txtDirector.getText().trim());
            pstmt.setInt(3, (Integer) spinnerAnio.getValue());
            pstmt.setInt(4, (Integer) spinnerDuracion.getValue());
            pstmt.setString(5, txtGenero.getText().trim());
            
            if (tienePrecio) {
                pstmt.setDouble(6, (Double) spinnerPrecio.getValue());
                pstmt.setInt(7, Integer.parseInt(idText));
            } else {
                pstmt.setInt(6, Integer.parseInt(idText));
            }
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                mostrarMensaje("Película modificada correctamente.",
                              "Modificación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                mostrarMensaje("No se pudo modificar la película. Verifique el ID.",
                              "Error en Modificación", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException ex) {
            mostrarMensaje("Error al modificar película: " + ex.getMessage(),
                          "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            mostrarMensaje("ID inválido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarPelicula() {
        String idText = txtId.getText().trim();
        
        if (idText.isEmpty()) {
            mostrarMensaje("Para eliminar, primero busque una película por ID.",
                          "ID Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea ELIMINAR permanentemente esta película?\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM " + TABLA + " WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(idText));
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                mostrarMensaje("Película eliminada correctamente.",
                              "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            } else {
                mostrarMensaje("No se pudo eliminar la película.",
                              "Error en Eliminación", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException ex) {
            mostrarMensaje("Error al eliminar película: " + ex.getMessage(),
                          "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // =========================================================================
    // MÉTODOS AUXILIARES MEJORADOS
    // =========================================================================
    
    private boolean validarCampos() {
        List<String> camposVacios = new ArrayList<>();
        
        if (txtTitulo.getText().trim().isEmpty()) camposVacios.add("Título");
        if (txtDirector.getText().trim().isEmpty()) camposVacios.add("Director");
        if (txtGenero.getText().trim().isEmpty()) camposVacios.add("Género");
        
        if (!camposVacios.isEmpty()) {
            String mensaje = "Los siguientes campos obligatorios están vacíos:\n" +
                           String.join("\n", camposVacios) +
                           "\n\nPor favor, complete todos los campos marcados con *.";
            
            mostrarMensaje(mensaje, "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private boolean verificarColumnaPrecio(Connection conn) {
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, TABLA, "precio");
            return columns.next();
        } catch (SQLException e) {
            return false;
        }
    }
    
    private int obtenerUltimoId(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()")) {
            return rs.next() ? rs.getInt(1) : -1;
        }
    }
    
    private void limpiarFormularioCompleto() {
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea limpiar todos los campos?",
            "Confirmar Limpieza", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            limpiarFormulario();
            mostrarMensaje("Formulario limpiado correctamente.",
                          "Limpieza Exitosa", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        txtId.setValue(null);
        txtTitulo.setText("");
        txtDirector.setText("");
        spinnerAnio.setValue(2023);
        spinnerDuracion.setValue(120);
        txtGenero.setText("");
        spinnerPrecio.setValue(5000.0);
        txtId.requestFocusInWindow();
    }
    
    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
    
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                "Error al cargar el driver de MySQL: " + e.getMessage(),
                "Error de Configuración", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            new SistemaGestionPeliculas().setVisible(true);
        });
    }
}