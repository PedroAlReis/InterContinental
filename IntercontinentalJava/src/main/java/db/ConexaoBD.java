package db;
import java.sql.*;
public class ConexaoBD{
    private static final String URL="jdbc:sqlite:loja.db";
    public static Connection conectar() throws SQLException{
        return DriverManager.getConnection(URL);
    }
}