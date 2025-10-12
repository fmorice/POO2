package com.computec.view;

import com.computec.dao.VentaDAO;
import com.computec.model.Venta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Formulario para listar todas las ventas
 */
public class ListadoVentasForm extends JDialog {
    
    private JTable tablaVentas;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar;
    private JButton btnCerrar;
    private VentaDAO ventaDAO;
    
    public ListadoVentasForm(Frame parent) {
        super(parent, "Historial de Ventas", true);
        this.ventaDAO = new VentaDAO();
        initComponents();
        setupDialog();
        cargarDatos();
    }
    
    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        
        // Título
        JLabel lblTitulo = new JLabel("Historial de Ventas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(51, 102, 153));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Tabla
        String[] columnas = {"ID", "Fecha", "Cliente", "RUT", "Equipo", "Tipo", "Precio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tablaVentas = new JTable(modeloTabla);
        tablaVentas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaVentas.setRowHeight(25);
        tablaVentas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaVentas.getTableHeader().setBackground(new Color(51, 102, 153));
        tablaVentas.getTableHeader().setForeground(Color.WHITE);
        
        // Ajustar columnas
        tablaVentas.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaVentas.getColumnModel().getColumn(1).setPreferredWidth(130);
        tablaVentas.getColumnModel().getColumn(2).setPreferredWidth(200);
        tablaVentas.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaVentas.getColumnModel().getColumn(4).setPreferredWidth(200);
        tablaVentas.getColumnModel().getColumn(5).setPreferredWidth(80);
        tablaVentas.getColumnModel().getColumn(6).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(tablaVentas);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnActualizar.setBackground(new Color(33, 150, 243));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> cargarDatos());
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(244, 67, 54));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> dispose());
        
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCerrar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private void setupDialog() {
        setSize(950, 500);
        setLocationRelativeTo(getParent());
        setResizable(true);
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        try {
            List<Venta> ventas = ventaDAO.obtenerTodas();
            
            for (Venta venta : ventas) {
                Object[] fila = {
                    venta.getId(),
                    sdf.format(venta.getFechaVenta()),
                    venta.getCliente().getNombreCompleto(),
                    venta.getCliente().getRut(),
                    venta.getEquipo().getDescripcion(),
                    venta.getEquipo().getTipo(),
                    String.format("$%,.0f", venta.getEquipo().getPrecio())
                };
                modeloTabla.addRow(fila);
            }
            
            if (ventas.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No hay ventas registradas", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar ventas: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
