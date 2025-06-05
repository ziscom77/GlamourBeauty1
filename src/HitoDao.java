import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HitoDao {

    public void save(HitoIPO h) throws SQLException {
        String sql = """
            INSERT INTO HitosIPO(nombre, descripcion, estado, fecha, documento_id)
            VALUES (?,?,?,?,?)
            """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, h.nombre());
            ps.setString(2, h.descripcion());
            ps.setString(3, h.estado());
            ps.setDate  (4, Date.valueOf(h.fecha()));
            ps.setInt   (5, h.documentoId());
            ps.executeUpdate();
        }
    }

    public HitoIPO find(int id) throws SQLException {
        String sql = "SELECT * FROM HitosIPO WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }
    public List<HitoIPO> findAll() throws SQLException {
        List<HitoIPO> out = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery("SELECT * FROM HitosIPO")) {
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }
    public List<HitoIPO> findByDocumento(int docId) throws SQLException {
        List<HitoIPO> out = new ArrayList<>();
        String sql = "SELECT * FROM HitosIPO WHERE documento_id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, docId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }

    public void updateEstado(int id, String estado) throws SQLException {
        String sql = "UPDATE HitosIPO SET estado=? WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM HitosIPO WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private HitoIPO map(ResultSet rs) throws SQLException {
        return new HitoIPO(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("estado"),
                rs.getDate("fecha").toLocalDate(),
                rs.getInt("documento_id"));
    }
}
