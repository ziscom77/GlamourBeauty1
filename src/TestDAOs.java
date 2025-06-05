import java.time.LocalDate;

public class TestDAOs {

    public static void main(String[] args) throws Exception {

        // 1. Proveedor
        ProveedorDao pDao = new ProveedorDao();
        pDao.save(new Proveedor(0, "KPMG", "kpmg@ipo.com"));
        System.out.println("Proveedores:");
        pDao.findAll().forEach(System.out::println);

        // 2. Inversionista
        InversionistaDao iDao = new InversionistaDao();
        iDao.save(new Inversionista(0, "State Street", 12000000));
        System.out.println("\nInversionistas:");
        iDao.findAll().forEach(System.out::println);

        // 3. Hito (suponiendo que ya existe un DocumentoIPO con id = 1)
        HitoDao hDao = new HitoDao();
        hDao.save(new HitoIPO(0, "Presentación CNMV",
                "Se presenta el folleto", "En curso",
                LocalDate.now(), 1));
        System.out.println("\nHitos del documento 1:");
        hDao.findByDocumento(1).forEach(System.out::println);
    }
}
