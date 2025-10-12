package com.computec.view;

import com.computec.dao.ClienteDAO;
import com.computec.model.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Formulario para listar todos los clientes registrados
 */
public class ListadoClientesForm extends JDialog {
    
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar;
    private JButton btnCerrar;
    private JButton btnEliminar;
    private ClienteDAO clienteDAO;
    
    public ListadoClientesForm(Frame parent) {
        super(parent, "Listado de Clientes", true);
        this.clienteDAO = new ClienteDAO();
        initComponents();
        setupDialog();
        cargarDatos();
    }
    
    private void initComponents() {
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        
        // Título
        JLabel lblTitulo = new JLabel("Listado de Clientes Registrados");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(51, 102, 153));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Crear tabla
        String[] columnas = {"ID", "RUT", "Nombre Completo", "Dirección", "Comuna", "Email", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaClientes.setRowHeight(25);
        tablaClientes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaClientes.getTableHeader().setBackground(new Color(51, 102, 153));
        tablaClientes.getTableHeader().setForeground(Color.WHITE);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Ajustar ancho de columnas
        tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(100); // RUT
        tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(200); // Nombre
        tablaClientes.getColumnModel().getColumn(3).setPreferredWidth(200); // Dirección
        tablaClientes.getColumnModel().getColumn(4).setPreferredWidth(100); // Comuna
        tablaClientes.getColumnModel().getColumn(5).setPreferredWidth(150); // Email
        tablaClientes.getColumnModel().getColumn(6).setPreferredWidth(100); // Teléfono
        
        // Scroll pane para la tabla
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnActualizar = crearBoton("Actualizar", new Color(33, 150, 243));
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatos();
            }
        });
        
        btnEliminar = crearBoton("Eliminar", new Color(255, 152, 0));
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarClienteSeleccionado();
            }
        });
        
        btnCerrar = crearBoton("Cerrar", new Color(244, 67, 54));
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCerrar);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }
    
    private void setupDialog() {
        setSize(1000, 600);
        setLocationRelativeTo(getParent());
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void cargarDatos() {
        // Limpiar tabla
        modeloTabla.setRowCount(0);
        
        try {
            System.out.println("🔄 Cargando datos de clientes...");
            
            // Obtener clientes de la base de datos
            List<Cliente> clientes = clienteDAO.obtenerTodos();
            
            System.out.println("📊 Clientes encontrados: " + clientes.size());
            
            // Agregar datos a la tabla
            for (Cliente cliente : clientes) {
                Object[] fila = {
                    cliente.getId(),
                    cliente.getRut(),
                    cliente.getNombreCompleto(),
                    cliente.getDireccion(),
                    cliente.getComuna(),
                    cliente.getEmail(),
                    cliente.getTelefono()
                };
                modeloTabla.addRow(fila);
            }
            
            // Mostrar mensaje con el resultado
            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No hay clientes registrados en la base de datos", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Mostrar mensaje de éxito en la consola
                System.out.println("✅ " + clientes.size() + " clientes cargados correctamente");
                
                // Opcional: mostrar mensaje al usuario
                // JOptionPane.showMessageDialog(this, 
                //     clientes.size() + " clientes cargados correctamente", 
                //     "Éxito", 
                //     JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error al cargar clientes: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error al cargar los clientes: " + e.getMessage() + 
                "\n\nVerifique:\n" +
                "1. Que la base de datos esté ejecutándose\n" +
                "2. Que las credenciales de conexión sean correctas\n" +
                "3. Que la tabla 'clientes' exista", 
                "Error de Conexión", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void eliminarClienteSeleccionado() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione un cliente de la lista",
                "Selección Requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idCliente = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombreCliente = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea eliminar al cliente?\n" +
            "Nombre: " + nombreCliente + "\n" +
            "ID: " + idCliente,
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                boolean eliminado = clienteDAO.eliminar(idCliente);
                if (eliminado) {
                    JOptionPane.showMessageDialog(this,
                        "Cliente eliminado correctamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos(); // Recargar la lista
                } else {
                    JOptionPane.showMessageDialog(this,
                        "No se pudo eliminar el cliente",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error al eliminar cliente: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Método para obtener el cliente seleccionado (útil para futuras extensiones)
    public Cliente getClienteSeleccionado() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idCliente = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            return clienteDAO.buscarPorId(idCliente);
        }
        return null;
    }
}