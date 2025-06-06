package dao;

import model.Nota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDao {

    /** Inserta o actualiza la nota de un empleado en un curso */
    public void upsert(Nota n) throws SQLException {
        String sql = """
            INSERT INTO Notas(empleado_id, curso_id, valor)
            VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE valor = VALUES(valor)
        """;
        try (Connection co = DatabaseConnection.getConnection();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setInt   (1, n.empleadoId());
            ps.setInt   (2, n.cursoId());
            ps.setDouble(3, n.valor());
            ps.executeUpdate();
        }
    }

    public List<Nota> findAll() throws SQLException {
        List<Nota> list = new ArrayList<>();
        String sql = "SELECT * FROM Notas";
        try (Connection co = DatabaseConnection.getConnection();
             ResultSet rs = co.createStatement().executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    /** Devuelve el promedio de todas las notas de un empleado */
    public double promedioPorEmpleado(int empleadoId) throws SQLException {
        String sql = "SELECT AVG(valor) AS prom FROM Notas WHERE empleado_id = ?";
        try (Connection co = DatabaseConnection.getConnection();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setInt(1, empleadoId);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getDouble("prom") : 0.0;
        }
    }

    private Nota map(ResultSet rs) throws SQLException {
        return new Nota(
                rs.getInt("empleado_id"),
                rs.getInt("curso_id"),
                rs.getDouble("valor")
        );
    }
}
