package dao;

import model.Matricula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDao {

    public void save(Matricula m) throws SQLException {
        String sql = "INSERT INTO Matriculas(empleado_id, curso_id) VALUES(?, ?)";
        try (Connection co = DatabaseConnection.getConnection();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setInt(1, m.empleadoId());
            ps.setInt(2, m.cursoId());
            ps.executeUpdate();
        }
    }

    public void delete(Matricula m) throws SQLException {
        String sql = "DELETE FROM Matriculas WHERE empleado_id = ? AND curso_id = ?";
        try (Connection co = DatabaseConnection.getConnection();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setInt(1, m.empleadoId());
            ps.setInt(2, m.cursoId());
            ps.executeUpdate();
        }
    }

    public List<Matricula> findAll() throws SQLException {
        List<Matricula> list = new ArrayList<>();
        String sql = "SELECT * FROM Matriculas";
        try (Connection co = DatabaseConnection.getConnection();
             ResultSet rs = co.createStatement().executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    private Matricula map(ResultSet rs) throws SQLException {
        return new Matricula(
                rs.getInt("empleado_id"),
                rs.getInt("curso_id")
        );
    }
}
