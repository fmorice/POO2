package com.computec.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    // Configura estos valores con tu base de datos real
    private String url = "jdbc:mysql://localhost:3306/sistema_computec";
    private String username = "root"; // tu usuario de MySQL
    private String password = "1234"; // tu contraseña de MySQL

    private DatabaseConnection() {
        // El constructor ya no lanza excepciones
    }

    public Connection getConnection() throws SQLException {
        // Si la conexión es nula o está cerrada, crear una nueva
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                this.connection = DriverManager.getConnection(url, username, password);
                System.out.println("✅ Nueva conexión a BD establecida");
            } catch (ClassNotFoundException ex) {
                System.err.println("❌ Error: Driver MySQL no encontrado");
                throw new SQLException("Driver MySQL no encontrado", ex);
            }
        }
        return connection;
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    // Método para cerrar la conexión explícitamente (opcional)
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("🔌 Conexión cerrada");
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}