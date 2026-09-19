package gt.edu.parqueo.modelo;

public class VehiculoPesado extends Vehiculo {
    public VehiculoPesado(String placa, String marca, String color) {
        super(placa, marca, color);
    }

    @Override
    public double obtenerTarifaPorHora() {
        return 12.00;
    }

    @Override
    public String obtenerTipo() {
        return "PESADO";
    }
}
