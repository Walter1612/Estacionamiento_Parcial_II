public class Automovil extends Vehiculo {
    private static final double TARIFA_POR_HORA = 10.00;

    public Automovil(String placa, String propietario, String horaIngreso, int horasUtilizadas) {
        super(placa, propietario, horaIngreso, horasUtilizadas);
    }

    @Override
    public double calcularCosto() {
        double costo = getHorasUtilizadas() * TARIFA_POR_HORA;
        if (getHorasUtilizadas() > 5) {
            costo = costo - (costo * 0.10);
        }
        return costo;
    }

    @Override
    public String getTipo() {
        return "Automóvil";
    }
}
