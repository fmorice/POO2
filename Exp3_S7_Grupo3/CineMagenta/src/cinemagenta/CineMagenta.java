package cinemagenta;

import javax.swing.SwingUtilities;

public class CineMagenta {
    public static void main(String[] args) {
        // Método correcto para ejecutar Swing desde el main
        SwingUtilities.invokeLater(() -> {
            CineSwingApp app = new CineSwingApp();
            app.setVisible(true);
        });
    }
}