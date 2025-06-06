package financial;/* financial.GlamourFinancialBD.java */
import dao.CotizacionDao;

import java.util.Map;

public class GlamourFinancialBD implements GlamourFinancial {

    private final CotizacionDao dao = new CotizacionDao();
    private final double accionesEnCirculacion;
    private final double valorEnLibros;

    public GlamourFinancialBD(double acciones, double libros) {
        this.accionesEnCirculacion = acciones;
        this.valorEnLibros         = libros;
    }

    @Override public double obtenerCotizacion(String mercado) {
        try { return dao.findAll().getOrDefault(mercado, 0.0); }
        catch (Exception e) { return 0; }
    }

    @Override public void actualizarCotizacion(String mercado, double valor) {
        try { dao.upsert(mercado, valor); }
        catch (Exception ignored) {}
    }

    @Override public double calcularValorDeMercado() {
        try {
            Map<String, Double> m = dao.findAll();
            double medio = m.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
            return medio * accionesEnCirculacion;
        } catch (Exception e) { return 0; }
    }

    @Override public double calcularPrecioValorEnLibros() {
        double vm = calcularValorDeMercado();
        return valorEnLibros == 0 ? 0 : vm / valorEnLibros;
    }
}
