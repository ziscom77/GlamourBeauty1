import java.sql.*;
import java.util.*;

public class CursoDao {

    public void save(Curso c) throws SQLException {
        String sql = "INSERT INTO Cursos(nombre, asignatura_id, periodo) VALUES(?,?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.nombre());
            ps.setInt   (2, c.asignaturaId());
            ps.setString(3, c.periodo());
            ps.executeUpdate();
        }
    }

    public List<Curso> findAll() throws SQLException {
        List<Curso> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Cursos")) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    /* …find(id), update, delete → igual que en tus otros DAOs… */

    private Curso map(ResultSet rs) throws SQLException {
        return new Curso(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getInt("asignatura_id"),
                rs.getString("periodo"));
    }
    /* dentro de CursoDao.java */

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Cursos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

}
