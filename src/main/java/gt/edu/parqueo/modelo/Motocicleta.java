package gt.edu.parqueo.modelo;

public class Motocicleta extends Vehiculo {
    public Motocicleta(String placa, String marca, String color) {
        super(placa, marca, color);
    }

    @Override
    public double obtenerTarifaPorHora() {
        return 5.00;
    }

    @Override
    public String obtenerTipo() {
        return "MOTOCICLETA";
    }
}
