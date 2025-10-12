package com.computec.view;

import com.computec.dao.VentaDAO;
import com.computec.model.Venta;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Reporte estadístico de ventas totales
 */
public class ReporteVentasTotalesForm extends JDialog {
    
    private JLabel lblCantidadVentas;
    private JLabel lblMontoTotal;
    private JLabel lblPromedioVenta;
    private JButton btnActualizar;
    private JButton btnCerrar;
    private VentaDAO ventaDAO;
    
    public ReporteVentasTotalesForm(Frame parent) {
        super(parent, "Reporte de Ventas Totales", true);
        this.ventaDAO = new VentaDAO();
        initComponents();
        setupDialog();
        calcularEstadisticas();
    }
    
    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panelPrincipal.setBackground(Color.WHITE);
        
        // Título
        JLabel lblTitulo = new JLabel("Estadísticas de Ventas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(51, 102, 153));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Panel estadísticas
        JPanel panelEstadisticas = new JPanel(new GridLayout(3, 1, 20, 20));
        panelEstadisticas.setBackground(Color.WHITE);
        panelEstadisticas.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        
        // Cantidad de ventas
        JPanel panelCantidad = crearPanelEstadistica("📊 Cantidad de Ventas", new Color(33, 150, 243));
        lblCantidadVentas = new JLabel("0", SwingConstants.CENTER);
        lblCantidadVentas.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblCantidadVentas.setForeground(new Color(33, 150, 243));
        panelCantidad.add(lblCantidadVentas, BorderLayout.CENTER);
        panelEstadisticas.add(panelCantidad);
        
        // Monto total
        JPanel panelMonto = crearPanelEstadistica("💰 Monto Total Recaudado", new Color(76, 175, 80));
        lblMontoTotal = new JLabel("$0", SwingConstants.CENTER);
        lblMontoTotal.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblMontoTotal.setForeground(new Color(76, 175, 80));
        panelMonto.add(lblMontoTotal, BorderLayout.CENTER);
        panelEstadisticas.add(panelMonto);
        
        // Promedio por venta
        JPanel panelPromedio = crearPanelEstadistica("📈 Promedio por Venta", new Color(255, 152, 0));
        lblPromedioVenta = new JLabel("$0", SwingConstants.CENTER);
        lblPromedioVenta.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblPromedioVenta.setForeground(new Color(255, 152, 0));
        panelPromedio.add(lblPromedioVenta, BorderLayout.CENTER);
        panelEstadisticas.add(panelPromedio);
        
        panelPrincipal.add(panelEstadisticas, BorderLayout.CENTER);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnActualizar.setBackground(new Color(33, 150, 243));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setPreferredSize(new Dimension(120, 40));
        btnActualizar.addActionListener(e -> calcularEstadisticas());
        
        btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrar.setBackground(new Color(244, 67, 54));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setPreferredSize(new Dimension(120, 40));
        btnCerrar.addActionListener(e -> dispose());
        
        panelBotones.add(btnActualizar);
        panelBotones.add(btnCerrar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private JPanel crearPanelEstadistica(String titulo, Color color) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 3),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(color);
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        return panel;
    }
    
    private void setupDialog() {
        setSize(600, 650);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }
    
    private void calcularEstadisticas() {
        try {
            List<Venta> ventas = ventaDAO.obtenerTodas();
            
            int cantidadVentas = ventas.size();
            double montoTotal = 0.0;
            
            for (Venta venta : ventas) {
                montoTotal += venta.getEquipo().getPrecio();
            }
            
            double promedioVenta = cantidadVentas > 0 ? montoTotal / cantidadVentas : 0.0;
            
            // Actualizar labels
            lblCantidadVentas.setText(String.valueOf(cantidadVentas));
            lblMontoTotal.setText(String.format("$%,.0f", montoTotal));
            lblPromedioVenta.setText(String.format("$%,.0f", promedioVenta));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al calcular estadísticas: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
