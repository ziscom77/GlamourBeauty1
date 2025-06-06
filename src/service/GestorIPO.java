package service;

import dao.DocumentoDao;
import dao.HitoDao;
import dao.InversionistaDao;
import dao.ProveedorDao;
import model.DocumentoIPO;
import model.HitoIPO;
import model.Inversionista;
import model.Proveedor;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class GestorIPO {

    private final ProveedorDao proveedorDao     = new ProveedorDao();
    private final InversionistaDao inversionistaDao = new InversionistaDao();
    private final DocumentoDao documentoDao     = new DocumentoDao();
    private final HitoDao hitoDao          = new HitoDao();

    /* ---------- PROVEEDORES ---------- */
    public void altaProveedor(String nombre, String contacto) throws SQLException {
        proveedorDao.save(new Proveedor(0, nombre, contacto));
    }
    public List<Proveedor> listaProveedores() throws SQLException {
        return proveedorDao.findAll();
    }
    public void borrarProveedor(int id) throws SQLException {
        proveedorDao.delete(id);
    }

    /* ---------- INVERSIONISTAS ---------- */
    public void altaInversionista(String nombre, double capital) throws SQLException {
        inversionistaDao.save(new Inversionista(0, nombre, capital));
    }
    public List<Inversionista> listaInversionistas() throws SQLException {
        return inversionistaDao.findAll();
    }
    public void borrarInversionista(int id) throws SQLException {
        inversionistaDao.delete(id);
    }

    /* ---------- DOCUMENTOS IPO ---------- */
    public void altaDocumento(String tipo, String descripcion,
                              Integer proveedorId, Integer inversionistaId) throws SQLException {
        documentoDao.save(new DocumentoIPO(0, tipo, descripcion, "Pendiente",
                proveedorId, inversionistaId));
    }
    public List<DocumentoIPO> listaDocumentos() throws SQLException {
        return documentoDao.findAll();
    }
    public void cambiarEstadoDocumento(int id, String estado) throws SQLException {
        documentoDao.updateEstado(id, estado);
    }
    public void borrarDocumento(int id) throws SQLException {
        documentoDao.delete(id);
    }

    /* ---------- HITOS ---------- */
    public void registrarHito(String nombre, String descr, String estado,
                              LocalDate fecha, int docId) throws SQLException {
        hitoDao.save(new HitoIPO(0, nombre, descr, estado, fecha, docId));
    }
    public List<HitoIPO> hitosPorDocumento(int docId) throws SQLException {
        return hitoDao.findByDocumento(docId);
    }
    public void completarHito(int id) throws SQLException {
        hitoDao.updateEstado(id, "Completado");
    }
    public void borrarHito(int id) throws SQLException {
        hitoDao.delete(id);
    }
}
