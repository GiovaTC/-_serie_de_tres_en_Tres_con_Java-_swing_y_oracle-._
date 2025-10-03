package com.example.serie;

import java.sql.*;
public class DBConnection {
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String USER = "system";
    private static final String PASSWORD = "Tapiero123";

    // metodo para obtener la conexion a oracle :.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // metodo para ejecutar el procedimiento almacenado
    public static void insertarNumero(int numero) {
        String sql = "{ call insertar_numero(?) }";
        try (Connection conn = getConnection();
            CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, numero);
            stmt.execute();
            System.out.println("numero insertado en oracle: "+ numero);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
