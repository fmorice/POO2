package com.computec.view;

import com.computec.dao.EquipoDAO;
import com.computec.model.Equipo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Formulario para registrar equipos (Desktop o Laptop)
 */
public class RegistroEquipoForm extends JDialog {
    
    // Componentes comunes
    private JComboBox<String> cboTipo;
    private JTextField txtDescripcion;
    private JTextField txtCpu;
    private JTextField txtDiscoDuro;
    private JTextField txtRam;
    private JTextField txtPrecio;
    
    // Componentes específicos para Desktop
    private JLabel lblPotenciaFuente;
    private JTextField txtPotenciaFuente;
    private JLabel lblFactorForma;
    private JComboBox<String> cboFactorForma;
    private JPanel panelDesktop;
    
    // Componentes específicos para Laptop
    private JLabel lblTamanoPantalla;
    private JTextField txtTamanoPantalla;
    private JLabel lblEsTouch;
    private JCheckBox chkEsTouch;
    private JLabel lblPuertosUSB;
    private JTextField txtPuertosUSB;
    private JPanel panelLaptop;
    
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnLimpiar;
    
    private EquipoDAO equipoDAO;
    private JPanel panelEspecifico; // Panel que cambia según el tipo
    
    public RegistroEquipoForm(Frame parent, String tipoEquipo) {
        super(parent, "Registrar Equipo", true);
        this.equipoDAO = new EquipoDAO();
        initComponents(tipoEquipo);
        setupDialog();
    }
    
    private void initComponents(String tipoInicial) {
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        
        // Título
        JLabel lblTitulo = new JLabel("Registro de Equipo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(51, 102, 153));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int fila = 0;
        
        // Tipo de equipo
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        JLabel lblTipo = new JLabel("Tipo de Equipo:");
        lblTipo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFormulario.add(lblTipo, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        cboTipo = new JComboBox<>(new String[]{"DESKTOP", "LAPTOP"});
        cboTipo.setSelectedItem(tipoInicial);
        cboTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCamposEspecificos();
            }
        });
        panelFormulario.add(cboTipo, gbc);
        fila++;
        
        // Descripción
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        JLabel lblDescripcion = new JLabel("Descripción/Modelo:");
        lblDescripcion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFormulario.add(lblDescripcion, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtDescripcion = new JTextField(20);
        panelFormulario.add(txtDescripcion, gbc);
        fila++;
        
        // CPU
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        JLabel lblCpu = new JLabel("CPU:");
        lblCpu.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFormulario.add(lblCpu, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtCpu = new JTextField(20);
        txtCpu.setToolTipText("Ej: Intel Core i5-12400F");
        panelFormulario.add(txtCpu, gbc);
        fila++;
        
        // Disco Duro (GB)
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        JLabel lblDiscoDuro = new JLabel("Disco Duro (GB):");
        lblDiscoDuro.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFormulario.add(lblDiscoDuro, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtDiscoDuro = new JTextField(20);
        txtDiscoDuro.setToolTipText("Ej: 1000");
        panelFormulario.add(txtDiscoDuro, gbc);
        fila++;
        
        // RAM (GB)
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        JLabel lblRam = new JLabel("RAM (GB):");
        lblRam.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFormulario.add(lblRam, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtRam = new JTextField(20);
        txtRam.setToolTipText("Ej: 16");
        panelFormulario.add(txtRam, gbc);
        fila++;
        
        // Precio
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFormulario.add(lblPrecio, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtPrecio = new JTextField(20);
        txtPrecio.setToolTipText("Ej: 599990");
        panelFormulario.add(txtPrecio, gbc);
        fila++;
        
        // Panel específico para Desktop/Laptop
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panelEspecifico = new JPanel();
        panelEspecifico.setLayout(new BorderLayout());
        panelEspecifico.setBackground(Color.WHITE);
        panelFormulario.add(panelEspecifico, gbc);
        
        // Crear paneles específicos
        crearPanelDesktop();
        crearPanelLaptop();
        
        // Mostrar panel según tipo inicial
        actualizarCamposEspecificos();
        
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarEquipo();
            }
        });
        
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLimpiar.setBackground(new Color(33, 150, 243));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCancelar);
        
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private void crearPanelDesktop() {
        panelDesktop = new JPanel();
        panelDesktop.setLayout(new GridBagLayout());
        panelDesktop.setBackground(new Color(240, 248, 255));
        panelDesktop.setBorder(BorderFactory.createTitledBorder("Características Desktop"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Potencia de fuente
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        lblPotenciaFuente = new JLabel("Potencia Fuente (W):");
        lblPotenciaFuente.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelDesktop.add(lblPotenciaFuente, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtPotenciaFuente = new JTextField(15);
        txtPotenciaFuente.setToolTipText("Ej: 650");
        panelDesktop.add(txtPotenciaFuente, gbc);
        
        // Factor de forma
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        lblFactorForma = new JLabel("Factor de Forma:");
        lblFactorForma.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelDesktop.add(lblFactorForma, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        cboFactorForma = new JComboBox<>(new String[]{"ATX", "EATX", "MicroATX"});
        panelDesktop.add(cboFactorForma, gbc);
    }
    
    private void crearPanelLaptop() {
        panelLaptop = new JPanel();
        panelLaptop.setLayout(new GridBagLayout());
        panelLaptop.setBackground(new Color(255, 248, 240));
        panelLaptop.setBorder(BorderFactory.createTitledBorder("Características Laptop"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Tamaño de pantalla
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        lblTamanoPantalla = new JLabel("Tamaño Pantalla (pulgadas):");
        lblTamanoPantalla.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelLaptop.add(lblTamanoPantalla, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtTamanoPantalla = new JTextField(15);
        txtTamanoPantalla.setToolTipText("Ej: 15.6");
        panelLaptop.add(txtTamanoPantalla, gbc);
        
        // Es Touch
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        lblEsTouch = new JLabel("Pantalla Touch:");
        lblEsTouch.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelLaptop.add(lblEsTouch, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        chkEsTouch = new JCheckBox("Sí");
        panelLaptop.add(chkEsTouch, gbc);
        
        // Puertos USB
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        lblPuertosUSB = new JLabel("Puertos USB:");
        lblPuertosUSB.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelLaptop.add(lblPuertosUSB, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtPuertosUSB = new JTextField(15);
        txtPuertosUSB.setToolTipText("Ej: 4");
        panelLaptop.add(txtPuertosUSB, gbc);
    }
    
    private void actualizarCamposEspecificos() {
        panelEspecifico.removeAll();
        
        String tipoSeleccionado = (String) cboTipo.getSelectedItem();
        
        if ("DESKTOP".equals(tipoSeleccionado)) {
            panelEspecifico.add(panelDesktop, BorderLayout.CENTER);
        } else {
            panelEspecifico.add(panelLaptop, BorderLayout.CENTER);
        }
        
        panelEspecifico.revalidate();
        panelEspecifico.repaint();
    }
    
    private void setupDialog() {
        setSize(550, 650);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }
    
    private void guardarEquipo() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        try {
            String tipo = (String) cboTipo.getSelectedItem();
            Equipo equipo = new Equipo();
            
            // Campos comunes
            equipo.setTipo(tipo);
            equipo.setDescripcion(txtDescripcion.getText().trim());
            equipo.setCpu(txtCpu.getText().trim());
            equipo.setDiscoDuroGB(Integer.parseInt(txtDiscoDuro.getText().trim()));
            equipo.setRamGB(Integer.parseInt(txtRam.getText().trim()));
            equipo.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
            
            // Campos específicos según tipo
            if ("DESKTOP".equals(tipo)) {
                equipo.setPotenciaFuente(Integer.parseInt(txtPotenciaFuente.getText().trim()));
                equipo.setFactorForma((String) cboFactorForma.getSelectedItem());
            } else { // LAPTOP
                equipo.setTamanoPantalla(Double.parseDouble(txtTamanoPantalla.getText().trim()));
                equipo.setEsTouch(chkEsTouch.isSelected());
                equipo.setPuertosUSB(Integer.parseInt(txtPuertosUSB.getText().trim()));
            }
            
            // Guardar en base de datos
            boolean resultado = equipoDAO.guardar(equipo);
            
            if (resultado) {
                JOptionPane.showMessageDialog(this, 
                    "Equipo guardado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al guardar el equipo", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Error en formato de números: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private boolean validarCampos() {
        // Validar campos comunes
        if (txtDescripcion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripción es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (txtCpu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El CPU es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (txtDiscoDuro.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El disco duro es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (txtRam.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La RAM es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (txtPrecio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El precio es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar campos específicos según tipo
        String tipo = (String) cboTipo.getSelectedItem();
        if ("DESKTOP".equals(tipo)) {
            if (txtPotenciaFuente.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La potencia de fuente es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else { // LAPTOP
            if (txtTamanoPantalla.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "El tamaño de pantalla es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (txtPuertosUSB.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Los puertos USB son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        // Validar que los números sean positivos
        try {
            int discoDuro = Integer.parseInt(txtDiscoDuro.getText().trim());
            int ram = Integer.parseInt(txtRam.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            
            if (discoDuro <= 0 || ram <= 0 || precio <= 0) {
                JOptionPane.showMessageDialog(this, "Los valores deben ser positivos", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if ("DESKTOP".equals(tipo)) {
                int potencia = Integer.parseInt(txtPotenciaFuente.getText().trim());
                if (potencia <= 0) {
                    JOptionPane.showMessageDialog(this, "La potencia debe ser positiva", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                double tamanoPantalla = Double.parseDouble(txtTamanoPantalla.getText().trim());
                int puertosUSB = Integer.parseInt(txtPuertosUSB.getText().trim());
                if (tamanoPantalla <= 0 || puertosUSB <= 0) {
                    JOptionPane.showMessageDialog(this, "Los valores deben ser positivos", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en formato de números", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void limpiarCampos() {
        txtDescripcion.setText("");
        txtCpu.setText("");
        txtDiscoDuro.setText("");
        txtRam.setText("");
        txtPrecio.setText("");
        txtPotenciaFuente.setText("");
        txtTamanoPantalla.setText("");
        txtPuertosUSB.setText("");
        chkEsTouch.setSelected(false);
        txtDescripcion.requestFocus();
    }
}
