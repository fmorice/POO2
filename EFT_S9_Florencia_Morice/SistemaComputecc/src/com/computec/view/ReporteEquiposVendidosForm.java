package com.computec.view;

import com.computec.dao.VentaDAO;
import com.computec.model.Venta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Reporte de equipos vendidos con filtro por tipo
 */
public class ReporteEquiposVendidosForm extends JDialog {
    
    private JComboBox<String> cboFiltroTipo;
    private JButton btnGenerar;
    private JTable tablaReporte;
    private DefaultTableModel modeloTabla;
    private JButton btnCerrar;
    private VentaDAO ventaDAO;
    
    public ReporteEquiposVendidosForm(Frame parent) {
        super(parent, "Reporte de Equipos Vendidos", true);
        this.ventaDAO = new VentaDAO();
        initComponents();
        setupDialog();
        generarReporte("TODOS");
    }
    
    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        
        // Título
        JLabel lblTitulo = new JLabel("Reporte de Equipos Vendidos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(51, 102, 153));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panel filtro
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFiltro.setBackground(Color.WHITE);
        JLabel lblFiltro = new JLabel("Filtrar por tipo:");
        lblFiltro.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cboFiltroTipo = new JComboBox<>(new String[]{"TODOS", "DESKTOP", "LAPTOP"});
        btnGenerar = new JButton("Generar Reporte");
        btnGenerar.setBackground(new Color(156, 39, 176));
        btnGenerar.setForeground(Color.WHITE);
        btnGenerar.setFocusPainted(false);
        btnGenerar.addActionListener(e -> generarReporte((String) cboFiltroTipo.getSelectedItem()));
        
        panelFiltro.add(lblFiltro);
        panelFiltro.add(cboFiltroTipo);
        panelFiltro.add(btnGenerar);
        
        // Panel norte
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(Color.WHITE);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelFiltro, BorderLayout.CENTER);
        panelPrincipal.add(panelNorte, BorderLayout.NORTH);
        
        // Tabla
        String[] columnas = {"Modelo Equipo", "Tipo", "Cliente", "Teléfono", "Email", "Precio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tablaReporte = new JTable(modeloTabla);
        tablaReporte.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaReporte.setRowHeight(25);
        tablaReporte.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaReporte.getTableHeader().setBackground(new Color(51, 102, 153));
        tablaReporte.getTableHeader().setForeground(Color.WHITE);
        
        // Ajustar columnas
        tablaReporte.getColumnModel().getColumn(0).setPreferredWidth(200);
        tablaReporte.getColumnModel().getColumn(1).setPreferredWidth(80);
        tablaReporte.getColumnModel().getColumn(2).setPreferredWidth(200);
        tablaReporte.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaReporte.getColumnModel().getColumn(4).setPreferredWidth(180);
        tablaReporte.getColumnModel().getColumn(5).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(tablaReporte);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(244, 67, 54));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> dispose());
        
        panelBotones.add(btnCerrar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private void setupDialog() {
        setSize(1000, 500);
        setLocationRelativeTo(getParent());
        setResizable(true);
    }
    
    private void generarReporte(String filtroTipo) {
        modeloTabla.setRowCount(0);
        
        try {
            List<Venta> ventas;
            if ("TODOS".equals(filtroTipo)) {
                ventas = ventaDAO.obtenerTodas();
            } else {
                ventas = ventaDAO.obtenerPorTipoEquipo(filtroTipo);
            }
            
            for (Venta venta : ventas) {
                Object[] fila = {
                    venta.getEquipo().getDescripcion(),
                    venta.getEquipo().getTipo(),
                    venta.getCliente().getNombreCompleto(),
                    venta.getCliente().getTelefono(),
                    venta.getCliente().getEmail(),
                    String.format("$%,.0f", venta.getEquipo().getPrecio())
                };
                modeloTabla.addRow(fila);
            }
            
            if (ventas.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No hay ventas para mostrar", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al generar reporte: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
