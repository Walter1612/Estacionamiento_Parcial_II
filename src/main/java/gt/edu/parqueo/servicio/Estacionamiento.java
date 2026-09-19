package gt.edu.parqueo.servicio;

import gt.edu.parqueo.datos.RegistroBD;
import gt.edu.parqueo.excepcion.VehiculoDuplicadoException;
import gt.edu.parqueo.excepcion.VehiculoNoEncontradoException;
import gt.edu.parqueo.modelo.RegistroEstacionamiento;
import gt.edu.parqueo.modelo.Vehiculo;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class Estacionamiento {
    // Colección estudiada en clase.
    private LinkedList<RegistroEstacionamiento> vehiculosDentro;
    private RegistroBD registroBD;

    public Estacionamiento() throws SQLException {
        registroBD = new RegistroBD();
        vehiculosDentro = registroBD.obtenerActivos();
    }

    public void registrarEntrada(Vehiculo vehiculo)
            throws VehiculoDuplicadoException, SQLException {
        if (buscarSinExcepcion(vehiculo.getPlaca()) != null) {
            throw new VehiculoDuplicadoException("El vehículo ya se encuentra dentro del estacionamiento.");
        }

        LocalDateTime entrada = LocalDateTime.now();
        int id = registroBD.guardarEntrada(vehiculo, entrada);
        RegistroEstacionamiento registro = new RegistroEstacionamiento(
                id, vehiculo, entrada, null, 0, 0
        );
        vehiculosDentro.add(registro);
    }

    public RegistroEstacionamiento registrarSalida(String placa)
            throws VehiculoNoEncontradoException, SQLException {
        RegistroEstacionamiento registro = buscar(placa);
        LocalDateTime salida = LocalDateTime.now();
        long minutos = Duration.between(registro.getFechaEntrada(), salida).toMinutes();
        int horas = (int) Math.ceil(minutos / 60.0);

        if (horas < 1) {
            horas = 1;
        }

        // Polimorfismo: el costo depende del tipo real del vehículo.
        double total = registro.getVehiculo().calcularCosto(horas);
        registro.registrarSalida(salida, horas, total);
        registroBD.guardarSalida(registro);
        vehiculosDentro.remove(registro);
        return registro;
    }

    public RegistroEstacionamiento buscar(String placa) throws VehiculoNoEncontradoException {
        RegistroEstacionamiento registro = buscarSinExcepcion(placa);
        if (registro == null) {
            throw new VehiculoNoEncontradoException("No se encontró un vehículo activo con esa placa.");
        }
        return registro;
    }

    private RegistroEstacionamiento buscarSinExcepcion(String placa) {
        for (RegistroEstacionamiento registro : vehiculosDentro) {
            if (registro.getVehiculo().getPlaca().equalsIgnoreCase(placa)) {
                return registro;
            }
        }
        return null;
    }

    public LinkedList<RegistroEstacionamiento> getVehiculosDentro() {
        return vehiculosDentro;
    }

    public LinkedList<RegistroEstacionamiento> obtenerHistorial() throws SQLException {
        return registroBD.obtenerHistorial();
    }

    public double obtenerIngresosTotales() throws SQLException {
        return registroBD.obtenerIngresosTotales();
    }
}
