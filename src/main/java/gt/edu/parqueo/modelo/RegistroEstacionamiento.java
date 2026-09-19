package gt.edu.parqueo.modelo;

import java.time.LocalDateTime;

public class RegistroEstacionamiento {
    private int id;
    private Vehiculo vehiculo;
    private LocalDateTime fechaEntrada;
    private LocalDateTime fechaSalida;
    private int horasCobradas;
    private double total;

    public RegistroEstacionamiento(int id, Vehiculo vehiculo, LocalDateTime fechaEntrada,
                                   LocalDateTime fechaSalida, int horasCobradas, double total) {
        this.id = id;
        this.vehiculo = vehiculo;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.horasCobradas = horasCobradas;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public int getHorasCobradas() {
        return horasCobradas;
    }

    public double getTotal() {
        return total;
    }

    public void registrarSalida(LocalDateTime fechaSalida, int horasCobradas, double total) {
        this.fechaSalida = fechaSalida;
        this.horasCobradas = horasCobradas;
        this.total = total;
    }
}
