package com.computec.view;

import com.computec.dao.EquipoDAO;
import com.computec.model.Equipo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Formulario para listar equipos con filtro por tipo
 */
public class ListadoEquiposForm extends JDialog {
    
    private JTable tablaEquipos;
    private DefaultTableModel modeloTabla;
    private JComboBox<String> cboFiltroTipo;
    private JButton btnFiltrar;
    private JButton btnCerrar;
    private EquipoDAO equipoDAO;
    
    public ListadoEquiposForm(Frame parent) {
        super(parent, "Listado de Equipos", true);
        this.equipoDAO = new EquipoDAO();
        initComponents();
        setupDialog();
        cargarDatos("TODOS");
    }
    
    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        
        // Título
        JLabel lblTitulo = new JLabel("Listado de Equipos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(51, 102, 153));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panel filtro
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFiltro.setBackground(Color.WHITE);
        JLabel lblFiltro = new JLabel("Filtrar por tipo:");
        lblFiltro.setFont(new Font("Segoe UI", Font.BOLD, 12));
        cboFiltroTipo = new JComboBox<>(new String[]{"TODOS", "DESKTOP", "LAPTOP"});
        btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBackground(new Color(33, 150, 243));
        btnFiltrar.setForeground(Color.WHITE);
        btnFiltrar.setFocusPainted(false);
        btnFiltrar.addActionListener(e -> cargarDatos((String) cboFiltroTipo.getSelectedItem()));
        
        panelFiltro.add(lblFiltro);
        panelFiltro.add(cboFiltroTipo);
        panelFiltro.add(btnFiltrar);
        
        // Panel norte (título + filtro)
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(Color.WHITE);
        panelNorte.add(lblTitulo, BorderLayout.NORTH);
        panelNorte.add(panelFiltro, BorderLayout.CENTER);
        panelPrincipal.add(panelNorte, BorderLayout.NORTH);
        
        // Tabla
        String[] columnas = {"ID", "Tipo", "Descripción", "CPU", "Disco(GB)", "RAM(GB)", "Precio"};
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
        
        JScrollPane scrollPane = new JScrollPane(tablaEquipos);
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
        setSize(900, 500);
        setLocationRelativeTo(getParent());
        setResizable(true);
    }
    
    private void cargarDatos(String filtroTipo) {
        modeloTabla.setRowCount(0);
        
        try {
            List<Equipo> equipos;
            if ("TODOS".equals(filtroTipo)) {
                equipos = equipoDAO.obtenerTodos();
            } else {
                equipos = equipoDAO.obtenerPorTipo(filtroTipo);
            }
            
            for (Equipo equipo : equipos) {
                Object[] fila = {
                    equipo.getId(),
                    equipo.getTipo(),
                    equipo.getDescripcion(),
                    equipo.getCpu(),
                    equipo.getDiscoDuroGB(),
                    equipo.getRamGB(),
                    String.format("$%,.0f", equipo.getPrecio())
                };
                modeloTabla.addRow(fila);
            }
            
            if (equipos.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No hay equipos registrados", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar equipos: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
