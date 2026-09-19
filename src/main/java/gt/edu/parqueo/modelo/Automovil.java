package gt.edu.parqueo.modelo;

public class Automovil extends Vehiculo {
    public Automovil(String placa, String marca, String color) {
        super(placa, marca, color);
    }

    @Override
    public double obtenerTarifaPorHora() {
        return 8.00;
    }

    @Override
    public String obtenerTipo() {
        return "AUTOMOVIL";
    }
}
