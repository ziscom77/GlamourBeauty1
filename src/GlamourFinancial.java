/* GlamourFinancial.java */
public interface GlamourFinancial {
    double obtenerCotizacion(String mercado);
    void   actualizarCotizacion(String mercado, double valor);
    double calcularValorDeMercado();
    double calcularPrecioValorEnLibros();
}
