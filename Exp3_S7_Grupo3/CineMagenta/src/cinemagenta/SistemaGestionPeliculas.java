package cinemagenta;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Esta clase proporciona una interfaz gráfica para gestionar las películas
 * en la base de datos, incluyendo funcionalidades de búsqueda, modificación
 * y eliminación con validaciones y transacciones seguras.
 *
 */
public class SistemaGestionPeliculas extends JFrame {
    
    // COMPONENTES DE LA INTERFAZ GRÁFICA
    
    // Campos de texto para los datos de la película
    private JTextField txtId;        
    private JTextField txtTitulo;    
    private JTextField txtDirector;  
    private JTextField txtAnio;      
    private JTextField txtDuracion;  
    private JTextField txtGenero;    
    
    // Botones de la aplicación
    private JButton btnBuscar;       // Botón para buscar una película
    private JButton btnModificar;    // Botón para modificar una película
    private JButton btnEliminar;     // Botón para eliminar una película
    private JButton btnLimpiar;      // Botón para limpiar el formulario
    
    // CONFIGURACIÓN DE LA BASE DE DATOS
    
    // Nombre de la tabla en la base de datos
    private static final String TABLA = "peliculas";
    
    // Nombres exactos de las columnas en la base de datos
    private static final String COL_ID = "id";
    private static final String COL_TITULO = "titulo";
    private static final String COL_DIRECTOR = "director";
    private static final String COL_ANIO = "anio";
    private static final String COL_DURACION = "duracion";
    private static final String COL_GENERO = "genero";
    
    // =========================================================================
    // CONSTRUCTOR PRINCIPAL
    // =========================================================================
    
    
     // Constructor que inicializa la interfaz y configura los eventos
   
    public SistemaGestionPeliculas() {
        inicializarInterfaz();    // Configura la apariencia de la ventana
        configurarEventos();      // Asigna acciones a los botones
    }
    
    // =========================================================================
    // INICIALIZACIÓN DE LA INTERFAZ GRÁFICA
    // =========================================================================
    
   
       // Configura todos los componentes visuales de la aplicación
     
    private void inicializarInterfaz() {
        // Configuración básica de la ventana principal
        setTitle("Sistema de Gestión de Películas - Cine Magenta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setSize(500, 400);        // Tamaño de la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        
        // Panel principal con bordes y espaciado
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel del formulario con diseño de grid para los campos
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
        
        // Crear borde con título para el panel del formulario
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLUE), 
            "Datos de la Película", 
            TitledBorder.LEFT, 
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12),
            Color.BLUE
        ));
        
        // =====================================================================
        // CREACIÓN Y CONFIGURACIÓN DE LOS CAMPOS DEL FORMULARIO
        // =====================================================================
        
        // Campo para ID de la película
        panelFormulario.add(new JLabel("ID Película:"));
        txtId = new JTextField();
        txtId.setToolTipText("Ingrese el número ID de la película (ej: 1, 2, 3)");
        panelFormulario.add(txtId);
        
        // Campo para título de la película
        panelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        txtTitulo.setToolTipText("Ingrese el título de la película");
        panelFormulario.add(txtTitulo);
        
        // Campo para director de la película
        panelFormulario.add(new JLabel("Director:"));
        txtDirector = new JTextField();
        txtDirector.setToolTipText("Ingrese el nombre del director");
        panelFormulario.add(txtDirector);
        
        // Campo para año de la película
        panelFormulario.add(new JLabel("Año:"));
        txtAnio = new JTextField();
        txtAnio.setToolTipText("Ingrese el año de estreno (ej: 1999)");
        panelFormulario.add(txtAnio);
        
        // Campo para duración de la película
        panelFormulario.add(new JLabel("Duración (min):"));
        txtDuracion = new JTextField();
        txtDuracion.setToolTipText("Ingrese la duración en minutos (ej: 120)");
        panelFormulario.add(txtDuracion);
        
        // Campo para género de la película
        panelFormulario.add(new JLabel("Género:"));
        txtGenero = new JTextField();
        txtGenero.setToolTipText("Ingrese el género (ej: Drama, Comedia, Acción)");
        panelFormulario.add(txtGenero);
        
        // =====================================================================
        // CREACIÓN Y CONFIGURACIÓN DE LOS BOTONES
        // =====================================================================
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        // Botón para buscar película
        btnBuscar = new JButton("Buscar Película");
        btnBuscar.setBackground(new Color(70, 130, 180));  // Azul
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setToolTipText("Buscar una película por su ID");
        
        // Botón para modificar película
        btnModificar = new JButton("Modificar Película");
        btnModificar.setBackground(new Color(34, 139, 34)); // Verde
        btnModificar.setForeground(Color.WHITE);
        btnModificar.setToolTipText("Guardar cambios en la película actual");
        
        // Botón para eliminar película
        btnEliminar = new JButton("Eliminar Película");
        btnEliminar.setBackground(new Color(220, 20, 60));  // Rojo
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setToolTipText("Eliminar permanentemente la película");
        
        // Botón para limpiar formulario
        btnLimpiar = new JButton("Limpiar Formulario");
        btnLimpiar.setBackground(new Color(255, 165, 0));   // Naranja
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setToolTipText("Limpiar todos los campos del formulario");
        
        // Agregar botones al panel
        panelBotones.add(btnBuscar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        // =====================================================================
        // ENSAMBLAJE FINAL DE LA INTERFAZ
        // =====================================================================
        
        // Agregar componentes al panel principal
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER); // Formulario en el centro
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);     // Botones en la parte inferior
        
        // Agregar panel principal a la ventana
        add(panelPrincipal);
    }
    
    // =========================================================================
    // CONFIGURACIÓN DE EVENTOS Y MANEJADORES
    // =========================================================================
    
    /**
     * Asigna las acciones correspondientes a cada botón
     */
    private void configurarEventos() {
        // Botón Buscar: Ejecuta la búsqueda de una película
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPelicula();
            }
        });
        
        // Botón Modificar: Ejecuta la modificación de una película
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarPelicula();
            }
        });
        
        // Botón Eliminar: Ejecuta la eliminación de una película
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarPelicula();
            }
        });
        
        // Botón Limpiar: Limpia todos los campos del formulario
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormularioCompleto();
            }
        });
    }
    
    // =========================================================================
    // MÉTODOS PRINCIPALES DE FUNCIONALIDAD
    // =========================================================================
    
    /**
     * Busca una película en la base de datos por su ID y carga sus datos en el formulario
     */
    private void buscarPelicula() {
        // Obtener y limpiar el ID ingresado
        String id = txtId.getText().trim();
        
        // Validar que el campo ID no esté vacío
        if (id.isEmpty()) {
            mostrarMensaje("Por favor, ingrese un ID de película para buscar.", 
                          "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Intentar buscar la película en la base de datos
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Consulta SQL para buscar por ID usando PreparedStatement (seguro contra SQL Injection)
            String sql = "SELECT * FROM " + TABLA + " WHERE " + COL_ID + " = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(id)); // Convertir ID a número
            
            // Ejecutar consulta y obtener resultados
            ResultSet rs = pstmt.executeQuery();
            
            // Verificar si se encontró la película
            if (rs.next()) {
                // Cargar datos de la película en los campos del formulario
                txtTitulo.setText(rs.getString(COL_TITULO));
                txtDirector.setText(rs.getString(COL_DIRECTOR));
                txtAnio.setText(String.valueOf(rs.getInt(COL_ANIO)));
                txtDuracion.setText(String.valueOf(rs.getInt(COL_DURACION)));
                txtGenero.setText(rs.getString(COL_GENERO));
                
                // Mostrar mensaje de éxito
                mostrarMensaje("Película encontrada correctamente.", 
                              "Búsqueda Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Mostrar error si no se encuentra la película
                mostrarMensaje("No se encontró ninguna película con el ID especificado.", 
                              "Película No Encontrada", JOptionPane.ERROR_MESSAGE);
                limpiarFormulario(); // Limpiar campos
            }
            
        } catch (SQLException ex) {
            // Manejar errores de base de datos
            mostrarMensaje("Error al buscar la película: " + ex.getMessage(), 
                          "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            // Manejar errores de formato numérico
            mostrarMensaje("El ID debe ser un número válido.", 
                          "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Modifica los datos de una película existente en la base de datos
     */
    private void modificarPelicula() {
        // Validar que todos los campos estén correctamente llenos
        if (!validarCampos()) {
            return; // Detener si hay errores de validación
        }
        
        // Pedir confirmación al usuario antes de modificar
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea modificar esta película?",
            "Confirmar Modificación",
            JOptionPane.YES_NO_OPTION);
        
        // Si el usuario cancela, detener la operación
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        // Intentar modificar la película en la base de datos
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Iniciar transacción para asegurar la integridad de los datos
            conn.setAutoCommit(false);
            
            try {
                // Consulta SQL para actualizar los datos de la película
                String sql = "UPDATE " + TABLA + " SET " + 
                           COL_TITULO + " = ?, " +
                           COL_DIRECTOR + " = ?, " + 
                           COL_ANIO + " = ?, " +
                           COL_DURACION + " = ?, " +
                           COL_GENERO + " = ? " +
                           "WHERE " + COL_ID + " = ?";
                
                PreparedStatement pstmt = conn.prepareStatement(sql);
                
                // Establecer parámetros de la consulta con los datos del formulario
                pstmt.setString(1, txtTitulo.getText());
                pstmt.setString(2, txtDirector.getText());
                pstmt.setInt(3, Integer.parseInt(txtAnio.getText()));
                pstmt.setInt(4, Integer.parseInt(txtDuracion.getText()));
                pstmt.setString(5, txtGenero.getText());
                pstmt.setInt(6, Integer.parseInt(txtId.getText()));
                
                // Ejecutar actualización y obtener número de filas afectadas
                int filasAfectadas = pstmt.executeUpdate();
                
                // Verificar si la modificación fue exitosa
                if (filasAfectadas > 0) {
                    conn.commit(); // Confirmar transacción
                    mostrarMensaje("Película modificada correctamente.",
                                  "Modificación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario(); // Limpiar formulario después del éxito
                } else {
                    conn.rollback(); // Revertir transacción en caso de error
                    mostrarMensaje("No se pudo modificar la película. Verifique el ID.",
                                  "Error en Modificación", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (SQLException ex) {
                conn.rollback(); // Revertir en caso de error SQL
                throw ex; // Relanzar la excepción para manejo externo
            }
            
        } catch (SQLException ex) {
            // Manejar errores de base de datos durante la modificación
            mostrarMensaje("Error al modificar la película: " + ex.getMessage(),
                          "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            // Manejar errores de formato numérico
            mostrarMensaje("Error en el formato de los números: Año y Duración deben ser números válidos.",
                          "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Elimina una película de la base de datos después de validar su existencia y confirmar
     */
    private void eliminarPelicula() {
        // Obtener y limpiar el ID ingresado
        String id = txtId.getText().trim();
        
        // Validar que el campo ID no esté vacío
        if (id.isEmpty()) {
            mostrarMensaje("Por favor, ingrese un ID de película para eliminar.",
                          "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Verificar que la película existe antes de intentar eliminarla
        if (!verificarExistenciaPelicula(id)) {
            mostrarMensaje("La película con el ID especificado no existe.",
                          "Película No Encontrada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Pedir confirmación explícita al usuario (eliminación es irreversible)
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea ELIMINAR permanentemente esta película?\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE); // Icono de advertencia
        
        // Si el usuario cancela, detener la operación
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        
        // Intentar eliminar la película de la base de datos
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Iniciar transacción para asegurar la integridad
            conn.setAutoCommit(false);
            
            try {
                // Consulta SQL para eliminar la película
                String sql = "DELETE FROM " + TABLA + " WHERE " + COL_ID + " = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, Integer.parseInt(id));
                
                // Ejecutar eliminación y obtener número de filas afectadas
                int filasAfectadas = pstmt.executeUpdate();
                
                // Verificar si la eliminación fue exitosa
                if (filasAfectadas > 0) {
                    conn.commit(); // Confirmar transacción
                    mostrarMensaje("Película eliminada correctamente.",
                                  "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    limpiarFormulario(); // Limpiar formulario después del éxito
                } else {
                    conn.rollback(); // Revertir transacción en caso de error
                    mostrarMensaje("No se pudo eliminar la película.",
                                  "Error en Eliminación", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (SQLException ex) {
                conn.rollback(); // Revertir en caso de error SQL
                throw ex; // Relanzar la excepción
            }
            
        } catch (SQLException ex) {
            // Manejar errores de base de datos durante la eliminación
            mostrarMensaje("Error al eliminar la película: " + ex.getMessage(),
                          "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            // Manejar errores de formato numérico
            mostrarMensaje("El ID debe ser un número válido.",
                          "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // =========================================================================
    // MÉTODOS AUXILIARES Y DE VALIDACIÓN
    // =========================================================================
    
    /**
     * Verifica si una película existe en la base de datos
     * @param id ID de la película a verificar
     */
    private boolean verificarExistenciaPelicula(String id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Consulta simple que retorna 1 si existe el registro
            String sql = "SELECT 1 FROM " + TABLA + " WHERE " + COL_ID + " = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(id));
            
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // true si hay resultados, false si no
            
        } catch (Exception ex) {
            // Mostrar error y asumir que no existe para prevenir eliminaciones erroneas
            mostrarMensaje("Error al verificar la existencia de la película: " + ex.getMessage(),
                          "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Valida que todos los campos del formulario estén correctamente llenos
     * @return true si todos los campos son válidos, false si hay errores
     */
    private boolean validarCampos() {
        // Lista para almacenar nombres de campos vacíos
        List<String> camposVacios = new ArrayList<>();
        
        // Verificar cada campo individualmente
        if (txtId.getText().trim().isEmpty()) camposVacios.add("ID Película");
        if (txtTitulo.getText().trim().isEmpty()) camposVacios.add("Título");
        if (txtDirector.getText().trim().isEmpty()) camposVacios.add("Director");
        if (txtAnio.getText().trim().isEmpty()) camposVacios.add("Año");
        if (txtDuracion.getText().trim().isEmpty()) camposVacios.add("Duración");
        if (txtGenero.getText().trim().isEmpty()) camposVacios.add("Género");
        
        // Si hay campos vacíos, mostrar mensaje de error
        if (!camposVacios.isEmpty()) {
            String mensaje = "Los siguientes campos están vacíos:\n" +
                           String.join("\n", camposVacios) +
                           "\n\nPor favor, complete todos los campos.";
            
            mostrarMensaje(mensaje, "Campos Incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar que los campos numéricos tengan formato correcto
        try {
            Integer.parseInt(txtId.getText());
            Integer.parseInt(txtAnio.getText());
            Integer.parseInt(txtDuracion.getText());
        } catch (NumberFormatException ex) {
            mostrarMensaje("Los campos 'ID', 'Año' y 'Duración' deben ser números válidos.",
                          "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Si pasa todas las validaciones, retornar true
        return true;
    }
    
    /**
     * Limpia todos los campos del formulario con confirmación del usuario
     */
    private void limpiarFormularioCompleto() {
        // Pedir confirmación antes de limpiar (pérdida de datos no guardados)
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea limpiar todos los campos?\n" +
            "Se perderán los datos no guardados.",
            "Confirmar Limpieza",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        // Solo limpiar si el usuario confirma
        if (confirmacion == JOptionPane.YES_OPTION) {
            limpiarFormulario(); // Limpiar campos
            
            // Mostrar confirmación de la acción
            mostrarMensaje("Formulario limpiado correctamente.\n" +
                          "Todos los campos están en blanco.",
                          "Limpieza Exitosa", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Limpia todos los campos del formulario (sin confirmación)
     * Usado internamente después de operaciones exitosas
     */
    private void limpiarFormulario() {
        txtId.setText("");
        txtTitulo.setText("");
        txtDirector.setText("");
        txtAnio.setText("");
        txtDuracion.setText("");
        txtGenero.setText("");
        txtId.requestFocusInWindow(); // Poner foco en el primer campo
    }
    
    /**
     * Muestra un mensaje al usuario en un cuadro de diálogo
     * @param mensaje Texto del mensaje a mostrar
     * @param titulo Título de la ventana del diálogo
     * @param tipo Tipo de mensaje (ERROR, WARNING, INFORMATION)
     */
    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
    
    // =========================================================================
    // MÉTODO MAIN PARA PRUEBAS INDEPENDIENTES
    // =========================================================================
    
    /**
     * Método principal para ejecutar esta ventana de forma independiente
     * @param args Argumentos de línea de comandos (no usados)
     */
    public static void main(String[] args) {
        // Cargar el driver de MySQL antes de abrir la interfaz
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                "Error al cargar el driver de MySQL: " + e.getMessage(),
                "Error de Configuración", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Ejecutar la interfaz en el hilo de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SistemaGestionPeliculas().setVisible(true);
            }
        });
    }
}