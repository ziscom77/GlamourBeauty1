public class TestFinanzas {
    public static void main(String[] args) {
        GlamourFinancial gf = new GlamourFinancialImpl(
                50_000_000,       // acciones
                200_000_000);     // valor en libros (€)

        gf.actualizarCotizacion("NYSE", 25.4);
        gf.actualizarCotizacion("BME", 24.9);

        System.out.println("Cotización NYSE: " + gf.obtenerCotizacion("NYSE"));
        System.out.println("Valor de mercado: " + gf.calcularValorDeMercado());
        System.out.println("P / Valor en libros: " + gf.calcularPrecioValorEnLibros());
    }
}
