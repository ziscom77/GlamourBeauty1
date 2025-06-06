package service;

import dao.*;
import model.*;

import java.sql.SQLException;
import java.util.List;

public class GestorFormacion {

    private final AsignaturaDao asiDao = new AsignaturaDao();
    private final CursoDao curDao = new CursoDao();
    private final EmpleadoDao empDao = new EmpleadoDao();
    private final MatriculaDao matDao = new MatriculaDao();
    private final NotaDao         notaDao= new NotaDao();
    private final CertificadoDao cerDao = new CertificadoDao();

    /* ---------- ASIGNATURAS ---------- */
    public void altaAsignatura(String nombre, int creditos) throws SQLException {
        asiDao.save(new Asignatura(0, nombre, creditos));
    }
    public List<Asignatura> listaAsignaturas() throws SQLException {
        return asiDao.findAll();
    }
    public void borrarAsignatura(int id) throws SQLException {
        asiDao.delete(id);
    }

    /* ---------- CURSOS ---------- */
    public void altaCurso(String nombre, int asignaturaId, String periodo) throws SQLException {
        curDao.save(new Curso(0, nombre, asignaturaId, periodo));
    }
    public List<Curso> listaCursos() throws SQLException {
        return curDao.findAll();
    }
    public void borrarCurso(int id) throws SQLException {
        curDao.delete(id);
    }

    /* ---------- EMPLEADOS ---------- */
    public void altaEmpleado(String nombre, String depto) throws SQLException {
        empDao.save(new Empleado(0, nombre, depto));
    }
    public List<Empleado> listaEmpleados() throws SQLException {
        return empDao.findAll();
    }
    public void borrarEmpleado(int id) throws SQLException {
        empDao.delete(id);
    }

    /* ---------- MATRÍCULAS ---------- */
    public void inscribir(int empleadoId, int cursoId) throws SQLException {
        matDao.save(new Matricula(empleadoId, cursoId));
    }
    public void desinscribir(int empleadoId, int cursoId) throws SQLException {
        matDao.delete(new Matricula(empleadoId, cursoId));
    }
    public List<Matricula> listaMatriculas() throws SQLException {
        return matDao.findAll();
    }

    /* ---------- NOTAS ---------- */
    public void registrarNota(int empleadoId, int cursoId, double valor) throws SQLException {
        notaDao.upsert(new Nota(empleadoId, cursoId, valor));
    }
    public List<Nota> listaNotas() throws SQLException {
        return notaDao.findAll();
    }
    public double promedioEmpleado(int empleadoId) throws SQLException {
        return notaDao.promedioPorEmpleado(empleadoId);
    }

    /* ---------- CERTIFICADOS ---------- */
    public void emitirCertificado(int empleadoId, String descripcion) throws SQLException {
        cerDao.save(new Certificado(0, empleadoId, descripcion));
    }
    public List<Certificado> listaCertificados() throws SQLException {
        return cerDao.findAll();
    }
}
