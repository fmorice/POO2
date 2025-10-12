package com.computec.patterns;

import com.computec.dao.ClienteDAO;
import com.computec.model.Cliente;
import javax.swing.JOptionPane;

/**
 * Comando para guardar un cliente - Implementación del patrón Command
 */
public class GuardarClienteCommand implements Command {
    private Cliente cliente;
    private ClienteDAO clienteDAO;
    
    public GuardarClienteCommand(Cliente cliente) {
        this.cliente = cliente;
        this.clienteDAO = new ClienteDAO();
    }
    
    @Override
    public void execute() {
        try {
            boolean resultado = clienteDAO.guardar(cliente);
            
            if (resultado) {
                JOptionPane.showMessageDialog(null, 
                    "Cliente guardado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Error al guardar el cliente", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
