import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class GestorIPO {


    private final DocumentoDao     documentoDao     = new DocumentoDao();
    private final ProveedorDao     proveedorDao     = new ProveedorDao();
    private final InversionistaDao inversionistaDao = new InversionistaDao();
    private final HitoDao          hitoDao          = new HitoDao();


    public void altaDocumento(String tipo,
                              String descripcion,
                              Integer proveedorId,
                              Integer inversionistaId) throws SQLException {

        DocumentoIPO doc = new DocumentoIPO(
                0,                // id autogenerado
                tipo,
                descripcion,
                "Pendiente",      // estado inicial
                proveedorId,
                inversionistaId);

        documentoDao.save(doc);
    }

    /** Lista todos los documentos existentes. */
    public List<DocumentoIPO> listaDocumentos() throws SQLException {
        return documentoDao.findAll();
    }

    /** Recupera un documento concreto por id. */
    public DocumentoIPO documentoPorId(int id) throws SQLException {
        return documentoDao.find(id);
    }


    public void altaProveedor(String nombre, String contacto) throws SQLException {
        proveedorDao.save(new Proveedor(0, nombre, contacto));
    }

    public List<Proveedor> listaProveedores() throws SQLException {
        return proveedorDao.findAll();
    }


    public void altaInversionista(String nombre, double capital) throws SQLException {
        inversionistaDao.save(new Inversionista(0, nombre, capital));
    }

    public List<Inversionista> listaInversionistas() throws SQLException {
        return inversionistaDao.findAll();
    }

    /**
     * Registra un nuevo hito asociado a un documento.
     *
     * @param nombre      título del hito
     * @param descripcion detalle del hito
     * @param estado      estado inicial (por ej. “Sin iniciar”)
     * @param fecha       fecha del hito (LocalDate.now() si quieres “hoy”)
     * @param docId       id del documento al que pertenece
     */
    public void registrarHito(String nombre,
                              String descripcion,
                              String estado,
                              LocalDate fecha,
                              int docId) throws SQLException {

        hitoDao.save(new HitoIPO(0, nombre, descripcion, estado, fecha, docId));
    }

    public List<HitoIPO> hitosPorDocumento(int docId) throws SQLException {
        return hitoDao.findByDocumento(docId);
    }

    /** Marca un hito como completado. */
    public void completarHito(int hitoId) throws SQLException {
        // Ejemplo rápido: cambiar estado a "Completado"
        HitoIPO h = hitoDao.find(hitoId);
        if (h != null) {
            HitoIPO hActualizado = new HitoIPO(
                    h.id(), h.nombre(), h.descripcion(),
                    "Completado", h.fecha(), h.documentoId());


        }
    }
}
