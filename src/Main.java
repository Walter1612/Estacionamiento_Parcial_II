import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner TECLADO = new Scanner(System.in);
    private static final ArrayList<Vehiculo> VEHICULOS = new ArrayList<>();
    private static final HashSet<String> PLACAS = new HashSet<>();

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    registrarVehiculo();
                    break;
                case 2:
                    mostrarTodos();
                    break;
                case 3:
                    buscarPorPlaca();
                    break;
                case 4:
                    mostrarMayorCosto();
                    break;
                case 5:
                    mostrarTotalGeneral();
                    break;
                case 6:
                    mostrarTotalesPorTipo();
                    break;
                case 7:
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Opción inválida. Seleccione un número del 1 al 7.");
            }
        } while (opcion != 7);
    }

    private static void mostrarMenu() {
        System.out.println("\n===== ESTACIONAMIENTO =====");
        System.out.println("1. Registrar vehículo");
        System.out.println("2. Mostrar todos los vehículos registrados");
        System.out.println("3. Buscar un vehículo por placa");
        System.out.println("4. Mostrar el vehículo que generó el mayor costo");
        System.out.println("5. Mostrar el total general recaudado");
        System.out.println("6. Mostrar el total recaudado por tipo");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int leerOpcion() {
        try {
            return Integer.parseInt(TECLADO.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void registrarVehiculo() {
        System.out.println("\n--- REGISTRAR VEHÍCULO ---");

        try {
            System.out.print("Tipo (1. Automóvil / 2. Motocicleta): ");
            int tipo = Integer.parseInt(TECLADO.nextLine().trim());

            if (tipo != 1 && tipo != 2) {
                throw new IllegalArgumentException("El tipo de vehículo no es válido.");
            }

            System.out.print("Placa: ");
            String placa = TECLADO.nextLine().trim().toUpperCase();
            if (placa.isEmpty()) {
                throw new IllegalArgumentException("La placa no puede estar vacía.");
            }
            if (PLACAS.contains(placa)) {
                throw new IllegalArgumentException("La placa ya se encuentra registrada.");
            }

            System.out.print("Nombre del propietario: ");
            String propietario = TECLADO.nextLine().trim();
            if (propietario.isEmpty()) {
                throw new IllegalArgumentException("El propietario no puede estar vacío.");
            }

            System.out.print("Hora de ingreso: ");
            String horaIngreso = TECLADO.nextLine().trim();
            if (horaIngreso.isEmpty()) {
                throw new IllegalArgumentException("La hora de ingreso no puede estar vacía.");
            }

            System.out.print("Cantidad de horas utilizadas: ");
            int horasUtilizadas = Integer.parseInt(TECLADO.nextLine().trim());
            if (horasUtilizadas <= 0) {
                throw new IllegalArgumentException("Las horas utilizadas deben ser mayores que cero.");
            }

            Vehiculo vehiculo;
            if (tipo == 1) {
                vehiculo = new Automovil(placa, propietario, horaIngreso, horasUtilizadas);
            } else {
                vehiculo = new Motocicleta(placa, propietario, horaIngreso, horasUtilizadas);
            }

            VEHICULOS.add(vehiculo);
            PLACAS.add(placa);
            System.out.printf("Vehículo registrado. Costo calculado: Q%.2f%n", vehiculo.calcularCosto());
        } catch (NumberFormatException e) {
            System.out.println("Error: debe ingresar un número cuando se solicita el tipo o las horas.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Intento de registro finalizado.");
        }
    }

    private static void mostrarTodos() {
        if (VEHICULOS.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }

        System.out.println("\n--- VEHÍCULOS REGISTRADOS ---");
        for (Vehiculo vehiculo : VEHICULOS) {
            vehiculo.mostrarInformacion();
            System.out.println("-----------------------------");
        }
    }

    private static void buscarPorPlaca() {
        System.out.print("Ingrese la placa que desea buscar: ");
        String placa = TECLADO.nextLine().trim().toUpperCase();

        if (placa.isEmpty()) {
            System.out.println("La placa no puede estar vacía.");
            return;
        }

        Vehiculo encontrado = encontrarVehiculo(placa);
        if (encontrado == null) {
            System.out.println("No se encontró un vehículo con esa placa.");
        } else {
            System.out.println("\n--- VEHÍCULO ENCONTRADO ---");
            encontrado.mostrarInformacion();
        }
    }

    private static Vehiculo encontrarVehiculo(String placa) {
        for (Vehiculo vehiculo : VEHICULOS) {
            if (vehiculo.getPlaca().equalsIgnoreCase(placa)) {
                return vehiculo;
            }
        }
        return null;
    }

    private static void mostrarMayorCosto() {
        if (VEHICULOS.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }

        Vehiculo mayor = VEHICULOS.get(0);
        for (Vehiculo vehiculo : VEHICULOS) {
            if (vehiculo.calcularCosto() > mayor.calcularCosto()) {
                mayor = vehiculo;
            }
        }

        System.out.println("\n--- VEHÍCULO CON MAYOR COSTO ---");
        mayor.mostrarInformacion();
    }

    private static void mostrarTotalGeneral() {
        double total = 0;
        for (Vehiculo vehiculo : VEHICULOS) {
            total += vehiculo.calcularCosto();
        }
        System.out.printf("Total general recaudado: Q%.2f%n", total);
    }

    private static void mostrarTotalesPorTipo() {
        if (VEHICULOS.isEmpty()) {
            System.out.println("No hay vehículos registrados.");
            return;
        }

        HashMap<String, Double> totales = new HashMap<>();
        for (Vehiculo vehiculo : VEHICULOS) {
            String tipo = vehiculo.getTipo();
            double acumulado = totales.getOrDefault(tipo, 0.0);
            totales.put(tipo, acumulado + vehiculo.calcularCosto());
        }

        System.out.println("\n--- TOTAL RECAUDADO POR TIPO ---");
        for (Map.Entry<String, Double> entrada : totales.entrySet()) {
            System.out.printf("%s: Q%.2f%n", entrada.getKey(), entrada.getValue());
        }
    }
}
