import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentoDao {

    public void save(DocumentoIPO d) throws SQLException {
        String sql = """
            INSERT INTO DocumentosIPO(tipo, descripcion, estado, proveedor_id, inversionista_id)
            VALUES (?,?,?,?,?)
            """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString (1, d.tipo());
            ps.setString (2, d.descripcion());
            ps.setString (3, d.estado());
            ps.setObject (4, d.proveedorId());
            ps.setObject (5, d.inversionistaId());
            ps.executeUpdate();
        }
    }

    public DocumentoIPO find(int id) throws SQLException {
        String sql = "SELECT * FROM DocumentosIPO WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }
    public List<DocumentoIPO> findAll() throws SQLException {
        List<DocumentoIPO> out = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery("SELECT * FROM DocumentosIPO")) {
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }

    public void updateEstado(int id, String estado) throws SQLException {
        String sql = "UPDATE DocumentosIPO SET estado=? WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setInt   (2, id);
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM DocumentosIPO WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private DocumentoIPO map(ResultSet rs) throws SQLException {
        return new DocumentoIPO(
                rs.getInt("id"),
                rs.getString("tipo"),
                rs.getString("descripcion"),
                rs.getString("estado"),
                (Integer) rs.getObject("proveedor_id"),
                (Integer) rs.getObject("inversionista_id"));
    }
}
