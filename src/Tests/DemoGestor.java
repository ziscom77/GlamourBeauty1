package Tests;

import service.GestorIPO;

import java.time.LocalDate;

public class DemoGestor {
    public static void main(String[] args) throws Exception {

        GestorIPO g = new GestorIPO();

        // 1. Crear proveedor y documento
        g.altaProveedor("EY", "ey@consulting.com");
        g.altaDocumento("Prospecto", "Borrador inicial", null, null);

        // 2. Listar documentos
        g.listaDocumentos().forEach(System.out::println);

        // 3. Añadir un hito al documento con id = 1
        g.registrarHito("Envío borrador a CNMV",
                "Se envía el primer borrador para revisión",
                "Sin iniciar",
                LocalDate.now(),
                1);

        // 4. Ver hitos del documento
        g.hitosPorDocumento(1).forEach(System.out::println);
    }
}
