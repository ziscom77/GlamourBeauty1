package dao;

import model.Asignatura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsignaturaDao {

    public void save(Asignatura a) throws SQLException {
        String sql = "INSERT INTO Asignaturas(nombre, creditos) VALUES(?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.nombre());
            ps.setInt   (2, a.creditos());
            ps.executeUpdate();
        }
    }

    public Asignatura find(int id) throws SQLException {
        String sql = "SELECT * FROM Asignaturas WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    public List<Asignatura> findAll() throws SQLException {
        List<Asignatura> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery("SELECT * FROM Asignaturas")) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public void update(Asignatura a) throws SQLException {
        String sql = "UPDATE Asignaturas SET nombre = ?, creditos = ? WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.nombre());
            ps.setInt   (2, a.creditos());
            ps.setInt   (3, a.id());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Asignaturas WHERE id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Asignatura map(ResultSet rs) throws SQLException {
        return new Asignatura(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getInt("creditos")
        );
    }
}
