import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentoDao {

    public void save(DocumentoIPO d) throws SQLException {
        String sql = """
            INSERT INTO DocumentosIPO
            (tipo, descripcion, estado, proveedor_id, inversionista_id)
            VALUES (?,?,?,?,?)
        """;

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, d.tipo());
            ps.setString(2, d.descripcion());
            ps.setString(3, d.estado());
            ps.setObject(4, d.proveedorId());      // admite null
            ps.setObject(5, d.inversionistaId()); // admite null
            ps.executeUpdate();
        }
    }

    public DocumentoIPO find(int id) throws SQLException {
        String sql = "SELECT * FROM DocumentosIPO WHERE id = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        }
    }

    public List<DocumentoIPO> findAll() throws SQLException {
        List<DocumentoIPO> lista = new ArrayList<>();
        String sql = "SELECT * FROM DocumentosIPO";

        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) lista.add(mapRow(rs));
        }
        return lista;
    }

    private DocumentoIPO mapRow(ResultSet rs) throws SQLException {
        return new DocumentoIPO(
                rs.getInt("id"),
                rs.getString("tipo"),
                rs.getString("descripcion"),
                rs.getString("estado"),
                (Integer) rs.getObject("proveedor_id"),
                (Integer) rs.getObject("inversionista_id"));
    }
}
