import java.sql.SQLException;
import java.util.List;

public class GestorFormacion {

    private final AsignaturaDao asiDao = new AsignaturaDao();
    private final CursoDao      curDao = new CursoDao();
    private final EmpleadoDao   empDao = new EmpleadoDao();
    private final MatriculaDao  matDao = new MatriculaDao();
    private final NotaDao       notaDao= new NotaDao();
    private final CertificadoDao cerDao= new CertificadoDao();

    /* --------- Inscripción --------- */
    public void inscribir(int empleadoId, int cursoId) throws SQLException {
        matDao.save(new Matricula(empleadoId, cursoId));
    }

    /* --------- Registrar nota ------- */
    public void registrarNota(int empleadoId, int cursoId, double valor) throws SQLException {
        notaDao.upsert(new Nota(empleadoId, cursoId, valor));
    }

    /* --------- Promedio empleado ---- */
    public double promedioEmpleado(int empleadoId) throws SQLException {
        return notaDao.promedioPorEmpleado(empleadoId);
    }

    /* --------- Emitir certificado ---- */
    public void emitirCertificado(int empleadoId, String descripcion) throws SQLException {
        cerDao.save(new Certificado(0, empleadoId, descripcion));
    }

    /* --------- Consultas de apoyo (listas) --- */
    public List<Asignatura>  listaAsignaturas()  throws SQLException { return asiDao.findAll(); }
    public List<Curso>       listaCursos()       throws SQLException { return curDao.findAll(); }
    public List<Empleado>    listaEmpleados()    throws SQLException { return empDao.findAll(); }
}
