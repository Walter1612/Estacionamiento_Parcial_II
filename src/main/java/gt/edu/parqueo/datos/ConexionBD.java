package gt.edu.parqueo.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:postgresql://localhost:5432/estacionamiento_db";
    private static final String USUARIO = "postgres";
    // Cambie este valor por la contraseña local de PostgreSQL antes de ejecutar.
    private static final String CONTRASENA = "CAMBIAR_AQUI";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
