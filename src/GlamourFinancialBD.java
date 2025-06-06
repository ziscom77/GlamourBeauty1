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
        catch (Exception e) { e.printStackTrace(); return 0.0; }
    }
    @Override public void actualizarCotizacion(String mercado, double valor) {
        try { dao.upsert(mercado, valor); }
        catch (Exception e) { e.printStackTrace(); }
    }
    @Override public double calcularValorDeMercado() {
        try {
            double medio = dao.findAll().values().stream()
                    .mapToDouble(Double::doubleValue)
                    .average().orElse(0);
            return medio * accionesEnCirculacion;
        } catch (Exception e) { e.printStackTrace(); return 0; }
    }
    @Override public double calcularPrecioValorEnLibros() {
        double vM = calcularValorDeMercado();
        return valorEnLibros == 0 ? 0 : vM / valorEnLibros;
    }
}
