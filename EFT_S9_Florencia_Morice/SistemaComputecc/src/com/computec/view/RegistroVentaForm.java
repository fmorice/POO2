package com.computec.view;

import com.computec.dao.ClienteDAO;
import com.computec.dao.EquipoDAO;
import com.computec.dao.VentaDAO;
import com.computec.model.Cliente;
import com.computec.model.Equipo;
import com.computec.model.Venta;
import com.computec.util.ValidadorRUT;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

/**
 * Formulario para registrar ventas
 */
public class RegistroVentaForm extends JDialog {
    
    private JTextField txtRutCliente;
    private JButton btnBuscarCliente;
    private JLabel lblDatosCliente;
    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;
    private JButton btnRegistrarVenta;
    private JButton btnCancelar;
    
    private ClienteDAO clienteDAO;
    private EquipoDAO equipoDAO;
    private VentaDAO ventaDAO;
    private Cliente clienteSeleccionado;
    
    public RegistroVentaForm(Frame parent) {
        super(parent, "Registrar Venta", true);
        this.clienteDAO = new ClienteDAO();
        this.equipoDAO = new EquipoDAO();
        this.ventaDAO = new VentaDAO();
        initComponents();
        setupDialog();
        cargarEquipos();
    }
    
    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        
        // Título
        JLabel lblTitulo = new JLabel("Registro de Venta");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(51, 102, 153));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Panel central
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBackground(Color.WHITE);
        
        // Panel cliente
        JPanel panelCliente = new JPanel(new GridBagLayout());
        panelCliente.setBackground(new Color(240, 248, 255));
        panelCliente.setBorder(BorderFactory.createTitledBorder("1. Buscar Cliente"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblRut = new JLabel("RUT Cliente:");
        lblRut.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelCliente.add(lblRut, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtRutCliente = new JTextField(15);
        txtRutCliente.setToolTipText("Formato: 12.345.678-9");
        panelCliente.add(txtRutCliente, gbc);
        
        gbc.gridx = 2;
        gbc.weightx = 0;
        btnBuscarCliente = new JButton("Buscar");
        btnBuscarCliente.setBackground(new Color(33, 150, 243));
        btnBuscarCliente.setForeground(Color.WHITE);
        btnBuscarCliente.setFocusPainted(false);
        btnBuscarCliente.addActionListener(e -> buscarCliente());
        panelCliente.add(btnBuscarCliente, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        lblDatosCliente = new JLabel("No hay cliente seleccionado");
        lblDatosCliente.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblDatosCliente.setForeground(Color.GRAY);
        panelCliente.add(lblDatosCliente, gbc);
        
        panelCentral.add(panelCliente, BorderLayout.NORTH);
        
        // Panel equipos
        JPanel panelEquipos = new JPanel(new BorderLayout());
        panelEquipos.setBackground(Color.WHITE);
        panelEquipos.setBorder(BorderFactory.createTitledBorder("2. Seleccionar Equipo"));
        
        String[] columnas = {"ID", "Tipo", "Descripción", "CPU", "RAM(GB)", "Precio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tablaEquipos = new JTable(modeloTabla);
        tablaEquipos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaEquipos.setRowHeight(25);
        tablaEquipos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaEquipos.getTableHeader().setBackground(new Color(51, 102, 153));
        tablaEquipos.getTableHeader().setForeground(Color.WHITE);
        tablaEquipos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tablaEquipos);
        panelEquipos.add(scrollPane, BorderLayout.CENTER);
        
        panelCentral.add(panelEquipos, BorderLayout.CENTER);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnRegistrarVenta = new JButton("Registrar Venta");
        btnRegistrarVenta.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRegistrarVenta.setBackground(new Color(76, 175, 80));
        btnRegistrarVenta.setForeground(Color.WHITE);
        btnRegistrarVenta.setFocusPainted(false);
        btnRegistrarVenta.addActionListener(e -> registrarVenta());
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dispose());
        
        panelBotones.add(btnRegistrarVenta);
        panelBotones.add(btnCancelar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private void setupDialog() {
        setSize(800, 600);
        setLocationRelativeTo(getParent());
        setResizable(true);
    }
    
    private void buscarCliente() {
        String rut = txtRutCliente.getText().trim();
        
        if (rut.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un RUT", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!ValidadorRUT.validarRUT(rut)) {
            JOptionPane.showMessageDialog(this, "El RUT ingresado no es válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            clienteSeleccionado = clienteDAO.buscarPorRut(rut);
            
            if (clienteSeleccionado != null) {
                lblDatosCliente.setText(String.format("Cliente: %s - Email: %s - Teléfono: %s", 
                    clienteSeleccionado.getNombreCompleto(),
                    clienteSeleccionado.getEmail(),
                    clienteSeleccionado.getTelefono()));
                lblDatosCliente.setForeground(new Color(76, 175, 80));
            } else {
                lblDatosCliente.setText("Cliente no encontrado");
                lblDatosCliente.setForeground(Color.RED);
                clienteSeleccionado = null;
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al buscar cliente: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void cargarEquipos() {
        modeloTabla.setRowCount(0);
        
        try {
            List<Equipo> equipos = equipoDAO.obtenerTodos();
            
            for (Equipo equipo : equipos) {
                Object[] fila = {
                    equipo.getId(),
                    equipo.getTipo(),
                    equipo.getDescripcion(),
                    equipo.getCpu(),
                    equipo.getRamGB(),
                    String.format("$%,.0f", equipo.getPrecio())
                };
                modeloTabla.addRow(fila);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar equipos: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void registrarVenta() {
        // Validar cliente seleccionado
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, 
                "Debe buscar y seleccionar un cliente primero", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar equipo seleccionado
        int filaSeleccionada = tablaEquipos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Debe seleccionar un equipo", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int equipoId = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            
            // Crear venta
            Venta venta = new Venta();
            venta.setClienteId(clienteSeleccionado.getId());
            venta.setEquipoId(equipoId);
            venta.setFechaVenta(new Date());
            
            // Guardar venta
            boolean resultado = ventaDAO.guardar(venta);
            
            if (resultado) {
                JOptionPane.showMessageDialog(this, 
                    "Venta registrada exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Limpiar formulario
                txtRutCliente.setText("");
                lblDatosCliente.setText("No hay cliente seleccionado");
                lblDatosCliente.setForeground(Color.GRAY);
                clienteSeleccionado = null;
                tablaEquipos.clearSelection();
                cargarEquipos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al registrar la venta", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al registrar venta: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
