public interface GlamourFinancial {

    /* CRUD de la cotización en memoria o BD */
    double obtenerCotizacion(String mercado);
    void   actualizarCotizacion(String mercado, double valor);

    /* Indicadores */
    double calcularValorDeMercado();      // precio medio × acciones en circulación
    double calcularPrecioValorEnLibros(); // valor de mercado / valor en libros
}
