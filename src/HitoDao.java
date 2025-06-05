import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HitoDao {

    /* INSERT: registra un hito para cierto documento */
    public void save(HitoIPO h) throws SQLException {
        String sql = """
            INSERT INTO HitosIPO (nombre, descripcion, estado, fecha, documento_id)
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

    /* Trae un hito por id */
    public HitoIPO find(int id) throws SQLException {
        String sql = "SELECT * FROM HitosIPO WHERE id = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        }
    }

    /* Trae todos los hitos de un documento concreto */
    public List<HitoIPO> findByDocumento(int docId) throws SQLException {
        List<HitoIPO> lista = new ArrayList<>();
        String sql = "SELECT * FROM HitosIPO WHERE documento_id = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, docId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapRow(rs));
        }
        return lista;
    }

    /* SELECT * (todos los hitos) */
    public List<HitoIPO> findAll() throws SQLException {
        List<HitoIPO> lista = new ArrayList<>();
        String sql = "SELECT * FROM HitosIPO";

        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) lista.add(mapRow(rs));
        }
        return lista;
    }

    private HitoIPO mapRow(ResultSet rs) throws SQLException {
        return new HitoIPO(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("estado"),
                rs.getDate("fecha").toLocalDate(),
                rs.getInt("documento_id"));
    }
}
