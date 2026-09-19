package gt.edu.parqueo.modelo;


public abstract class Vehiculo {
    private String placa;
    private String marca;
    private String color;

    public Vehiculo(String placa, String marca, String color) {
        this.placa = placa;
        this.marca = marca;
        this.color = color;
    }

    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getColor() {
        return color;
    }

    // Cada clase hija sobrescribe estos métodos.
    public abstract double obtenerTarifaPorHora();

    public abstract String obtenerTipo();

    public double calcularCosto(int horas) {
        return obtenerTarifaPorHora() * horas;
    }

    @Override
    public String toString() {
        return placa + " | " + obtenerTipo() + " | " + marca + " | " + color;
    }
}
