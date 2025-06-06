import java.util.HashMap;
import java.util.Map;

public class GlamourFinancialImpl implements GlamourFinancial {

    private final Map<String, Double> precios = new HashMap<>();
    private final double accionesEnCirculacion;
    private final double valorEnLibros;

    public GlamourFinancialImpl(double accionesEnCirculacion,
                                double valorEnLibros) {
        this.accionesEnCirculacion = accionesEnCirculacion;
        this.valorEnLibros         = valorEnLibros;
    }

    /* ---------- CRUD simple ---------- */
    @Override public double obtenerCotizacion(String mercado) {
        return precios.getOrDefault(mercado, 0.0);
    }
    @Override public void actualizarCotizacion(String mercado, double valor) {
        precios.put(mercado, valor);
    }

    /* ---------- Indicadores ---------- */
    @Override public double calcularValorDeMercado() {
        double precioMedio = precios.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);
        return precioMedio * accionesEnCirculacion;
    }
    @Override public double calcularPrecioValorEnLibros() {
        double vMercado = calcularValorDeMercado();
        return valorEnLibros == 0 ? 0 : vMercado / valorEnLibros;
    }
}
