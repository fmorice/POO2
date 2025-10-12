package com.computec;

import com.computec.view.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal para ejecutar el Sistema Computec
 * Punto de entrada de la aplicación
 */
public class Main {
    
    public static void main(String[] args) {
        // Establecer el Look and Feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("No se pudo establecer el Look and Feel del sistema");
            e.printStackTrace();
        }
        
        // Ejecutar la aplicación en el Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Crear y mostrar la ventana principal
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.setVisible(true);
                    
                    System.out.println("===========================================");
                    System.out.println("Sistema Computec iniciado correctamente");
                    System.out.println("===========================================");
                    
                } catch (Exception e) {
                    System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
