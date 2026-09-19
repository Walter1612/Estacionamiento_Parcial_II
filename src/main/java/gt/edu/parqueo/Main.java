package gt.edu.parqueo;

import gt.edu.parqueo.excepcion.DatoInvalidoException;
import gt.edu.parqueo.excepcion.VehiculoDuplicadoException;
import gt.edu.parqueo.excepcion.VehiculoNoEncontradoException;
import gt.edu.parqueo.modelo.Automovil;
import gt.edu.parqueo.modelo.Motocicleta;
import gt.edu.parqueo.modelo.RegistroEstacionamiento;
import gt.edu.parqueo.modelo.Vehiculo;
import gt.edu.parqueo.modelo.VehiculoPesado;
import gt.edu.parqueo.servicio.Estacionamiento;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    private static Scanner teclado = new Scanner(System.in);
    private static DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {
        try {
            Estacionamiento estacionamiento = new Estacionamiento();
            mostrarMenu(estacionamiento);
        } catch (SQLException e) {
            System.out.println("No fue posible conectar con PostgreSQL.");
            System.out.println("Detalle: " + e.getMessage());
            System.out.println("Revise la contraseña en la clase ConexionBD.");
        }
    }

    public static void mostrarMenu(Estacionamiento estacionamiento) {
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n===== ESTACIONAMIENTO =====");
            System.out.println("1. Registrar entrada");
            System.out.println("2. Registrar salida");
            System.out.println("3. Mostrar vehículos dentro");
            System.out.println("4. Buscar vehículo por placa");
            System.out.println("5. Mostrar historial");
            System.out.println("6. Mostrar ingresos totales");
            System.out.println("0. Salir");
            opcion = leerNumero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1:
                        registrarEntrada(estacionamiento);
                        break;
                    case 2:
                        registrarSalida(estacionamiento);
                        break;
                    case 3:
                        mostrarRegistros(estacionamiento.getVehiculosDentro());
                        break;
                    case 4:
                        buscarVehiculo(estacionamiento);
                        break;
                    case 5:
                        mostrarRegistros(estacionamiento.obtenerHistorial());
                        break;
                    case 6:
                        System.out.printf("Ingresos acumulados: Q%.2f%n", estacionamiento.obtenerIngresosTotales());
                        break;
                    case 0:
                        System.out.println("Programa finalizado.");
                        break;
                    default:
                        System.out.println("La opción seleccionada no existe.");
                }
            } catch (DatoInvalidoException e) {
                System.out.println("Dato inválido: " + e.getMessage());
            } catch (VehiculoDuplicadoException e) {
                System.out.println("Entrada rechazada: " + e.getMessage());
            } catch (VehiculoNoEncontradoException e) {
                System.out.println("Búsqueda: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Error de PostgreSQL: " + e.getMessage());
            }
        }
    }

    public static void registrarEntrada(Estacionamiento estacionamiento)
            throws DatoInvalidoException, VehiculoDuplicadoException, SQLException {
        System.out.println("\n1. Motocicleta");
        System.out.println("2. Automóvil");
        System.out.println("3. Vehículo pesado");
        int tipo = leerNumero("Seleccione el tipo: ");

        String placa = validarTexto(leerTexto("Placa: "), "placa").toUpperCase();
        String marca = validarTexto(leerTexto("Marca: "), "marca");
        String color = validarTexto(leerTexto("Color: "), "color");

        Vehiculo vehiculo;
        switch (tipo) {
            case 1:
                vehiculo = new Motocicleta(placa, marca, color);
                break;
            case 2:
                vehiculo = new Automovil(placa, marca, color);
                break;
            case 3:
                vehiculo = new VehiculoPesado(placa, marca, color);
                break;
            default:
                throw new DatoInvalidoException("El tipo de vehículo no existe.");
        }

        estacionamiento.registrarEntrada(vehiculo);
        System.out.println("La entrada fue registrada correctamente.");
    }

    public static void registrarSalida(Estacionamiento estacionamiento)
            throws DatoInvalidoException, VehiculoNoEncontradoException, SQLException {
        String placa = validarTexto(leerTexto("Placa del vehículo: "), "placa").toUpperCase();
        RegistroEstacionamiento registro = estacionamiento.registrarSalida(placa);
        System.out.println("Salida registrada correctamente.");
        System.out.println("Horas cobradas: " + registro.getHorasCobradas());
        System.out.printf("Total: Q%.2f%n", registro.getTotal());
    }

    public static void buscarVehiculo(Estacionamiento estacionamiento)
            throws DatoInvalidoException, VehiculoNoEncontradoException {
        String placa = validarTexto(leerTexto("Placa a buscar: "), "placa").toUpperCase();
        RegistroEstacionamiento registro = estacionamiento.buscar(placa);
        mostrarRegistro(registro);
    }

    public static void mostrarRegistros(LinkedList<RegistroEstacionamiento> registros) {
        if (registros.isEmpty()) {
            System.out.println("No hay registros para mostrar.");
        } else {
            for (RegistroEstacionamiento registro : registros) {
                mostrarRegistro(registro);
            }
        }
    }

    public static void mostrarRegistro(RegistroEstacionamiento registro) {
        String salida = "DENTRO";
        if (registro.getFechaSalida() != null) {
            salida = registro.getFechaSalida().format(formatoFecha);
        }

        System.out.println("----------------------------------------");
        System.out.println("Registro: " + registro.getId());
        System.out.println("Vehículo: " + registro.getVehiculo());
        System.out.println("Entrada: " + registro.getFechaEntrada().format(formatoFecha));
        System.out.println("Salida: " + salida);
        System.out.printf("Total: Q%.2f%n", registro.getTotal());
    }

    public static String validarTexto(String texto, String nombreCampo) throws DatoInvalidoException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new DatoInvalidoException("El campo " + nombreCampo + " es obligatorio.");
        }
        return texto.trim();
    }

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine();
    }

    public static int leerNumero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un número entero.");
            }
        }
    }
}
