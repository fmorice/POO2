package com.computec.view;

import com.computec.dao.ClienteDAO;
import com.computec.model.Cliente;
import com.computec.patterns.Command;
import com.computec.patterns.GuardarClienteCommand;
import com.computec.util.ValidadorRUT;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Formulario para registrar nuevos clientes
 */
public class RegistroClienteForm extends JDialog {
    
    // Componentes del formulario
    private JTextField txtRut;
    private JTextField txtNombre;
    private JTextField txtDireccion;
    private JTextField txtComuna;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JButton btnLimpiar;
    
    private ClienteDAO clienteDAO;
    
    public RegistroClienteForm(Frame parent) {
        super(parent, "Registrar Cliente", true);
        this.clienteDAO = new ClienteDAO();
        initComponents();
        setupDialog();
    }
    
    private void initComponents() {
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.WHITE);
        
        // Título
        JLabel lblTitulo = new JLabel("Registro de Cliente");
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
        
        // RUT
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel lblRut = new JLabel("RUT:");
        lblRut.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFormulario.add(lblRut, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtRut = new JTextField(20);
        txtRut.setToolTipText("Formato: 12.345.678-9");
        panelFormulario.add(txtRut, gbc);
        
        // Nombre Completo
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel lblNombre = new JLabel("Nombre Completo:");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFormulario.add(lblNombre, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);
        
        // Dirección
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFormulario.add(lblDireccion, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtDireccion = new JTextField(20);
        panelFormulario.add(txtDireccion, gbc);
        
        // Comuna
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        JLabel lblComuna = new JLabel("Comuna:");
        lblComuna.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFormulario.add(lblComuna, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtComuna = new JTextField(20);
        panelFormulario.add(txtComuna, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFormulario.add(lblEmail, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtEmail = new JTextField(20);
        txtEmail.setToolTipText("ejemplo@correo.com");
        panelFormulario.add(txtEmail, gbc);
        
        // Teléfono
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.3;
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelFormulario.add(lblTelefono, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtTelefono = new JTextField(20);
        txtTelefono.setToolTipText("+56912345678");
        panelFormulario.add(txtTelefono, gbc);
        
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
                guardarCliente();
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
    
    private void setupDialog() {
        setSize(500, 500);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }
    
    private void guardarCliente() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        // Crear objeto cliente
        Cliente cliente = new Cliente();
        cliente.setRut(txtRut.getText().trim());
        cliente.setNombreCompleto(txtNombre.getText().trim());
        cliente.setDireccion(txtDireccion.getText().trim());
        cliente.setComuna(txtComuna.getText().trim());
        cliente.setEmail(txtEmail.getText().trim());
        cliente.setTelefono(txtTelefono.getText().trim());
        
        // Usar patrón Command para guardar
        Command guardarCommand = new GuardarClienteCommand(cliente);
        guardarCommand.execute();
        
        // Limpiar campos después de guardar
        limpiarCampos();
    }
    
    private boolean validarCampos() {
        // Validar que todos los campos estén llenos
        if (txtRut.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El RUT es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtRut.requestFocus();
            return false;
        }
        
        // Validar RUT chileno
        if (!ValidadorRUT.validarRUT(txtRut.getText().trim())) {
            JOptionPane.showMessageDialog(this, "El RUT ingresado no es válido", "Error", JOptionPane.ERROR_MESSAGE);
            txtRut.requestFocus();
            return false;
        }
        
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre completo es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }
        
        if (txtDireccion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La dirección es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
            txtDireccion.requestFocus();
            return false;
        }
        
        if (txtComuna.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La comuna es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
            txtComuna.requestFocus();
            return false;
        }
        
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El email es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }
        
        // Validar formato de email
        if (!txtEmail.getText().trim().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "El formato del email no es válido", "Error", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }
        
        if (txtTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El teléfono es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtTelefono.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void limpiarCampos() {
        txtRut.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtComuna.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtRut.requestFocus();
    }
}
