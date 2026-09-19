package gt.edu.parqueo.datos;

import gt.edu.parqueo.modelo.Automovil;
import gt.edu.parqueo.modelo.Motocicleta;
import gt.edu.parqueo.modelo.RegistroEstacionamiento;
import gt.edu.parqueo.modelo.Vehiculo;
import gt.edu.parqueo.modelo.VehiculoPesado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class RegistroBD {

    public int guardarEntrada(Vehiculo vehiculo, LocalDateTime fechaEntrada) throws SQLException {
        String sql = "INSERT INTO registro_estacionamiento "
                + "(placa, marca, color, tipo, fecha_entrada) VALUES (?, ?, ?, ?, ?)";

        Connection conexion = ConexionBD.conectar();
        PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        sentencia.setString(1, vehiculo.getPlaca());
        sentencia.setString(2, vehiculo.getMarca());
        sentencia.setString(3, vehiculo.getColor());
        sentencia.setString(4, vehiculo.obtenerTipo());
        sentencia.setTimestamp(5, Timestamp.valueOf(fechaEntrada));
        sentencia.executeUpdate();

        ResultSet resultado = sentencia.getGeneratedKeys();
        int id = 0;
        if (resultado.next()) {
            id = resultado.getInt(1);
        }

        resultado.close();
        sentencia.close();
        conexion.close();
        return id;
    }

    public void guardarSalida(RegistroEstacionamiento registro) throws SQLException {
        String sql = "UPDATE registro_estacionamiento "
                + "SET fecha_salida = ?, horas_cobradas = ?, total = ? WHERE id = ?";

        Connection conexion = ConexionBD.conectar();
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setTimestamp(1, Timestamp.valueOf(registro.getFechaSalida()));
        sentencia.setInt(2, registro.getHorasCobradas());
        sentencia.setDouble(3, registro.getTotal());
        sentencia.setInt(4, registro.getId());
        sentencia.executeUpdate();
        sentencia.close();
        conexion.close();
    }

    public LinkedList<RegistroEstacionamiento> obtenerActivos() throws SQLException {
        String sql = "SELECT * FROM registro_estacionamiento "
                + "WHERE fecha_salida IS NULL ORDER BY fecha_entrada";
        return consultarRegistros(sql);
    }

    public LinkedList<RegistroEstacionamiento> obtenerHistorial() throws SQLException {
        String sql = "SELECT * FROM registro_estacionamiento ORDER BY fecha_entrada DESC";
        return consultarRegistros(sql);
    }

    public double obtenerIngresosTotales() throws SQLException {
        String sql = "SELECT SUM(total) AS ingresos FROM registro_estacionamiento";
        Connection conexion = ConexionBD.conectar();
        Statement sentencia = conexion.createStatement();
        ResultSet resultado = sentencia.executeQuery(sql);
        double ingresos = 0;

        if (resultado.next()) {
            ingresos = resultado.getDouble("ingresos");
        }

        resultado.close();
        sentencia.close();
        conexion.close();
        return ingresos;
    }

    private LinkedList<RegistroEstacionamiento> consultarRegistros(String sql) throws SQLException {
        LinkedList<RegistroEstacionamiento> lista = new LinkedList<RegistroEstacionamiento>();
        Connection conexion = ConexionBD.conectar();
        Statement sentencia = conexion.createStatement();
        ResultSet resultado = sentencia.executeQuery(sql);

        while (resultado.next()) {
            Vehiculo vehiculo = crearVehiculo(
                    resultado.getString("tipo"),
                    resultado.getString("placa"),
                    resultado.getString("marca"),
                    resultado.getString("color")
            );

            Timestamp salidaSQL = resultado.getTimestamp("fecha_salida");
            LocalDateTime salida = null;
            if (salidaSQL != null) {
                salida = salidaSQL.toLocalDateTime();
            }

            RegistroEstacionamiento registro = new RegistroEstacionamiento(
                    resultado.getInt("id"),
                    vehiculo,
                    resultado.getTimestamp("fecha_entrada").toLocalDateTime(),
                    salida,
                    resultado.getInt("horas_cobradas"),
                    resultado.getDouble("total")
            );
            lista.add(registro);
        }

        resultado.close();
        sentencia.close();
        conexion.close();
        return lista;
    }

    private Vehiculo crearVehiculo(String tipo, String placa, String marca, String color) {
        if (tipo.equals("MOTOCICLETA")) {
            return new Motocicleta(placa, marca, color);
        } else if (tipo.equals("PESADO")) {
            return new VehiculoPesado(placa, marca, color);
        } else {
            return new Automovil(placa, marca, color);
        }
    }
}
