import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDao {

    /* ---------- INSERT ---------- */
    public void save(Proveedor p) throws SQLException {
        String sql = "INSERT INTO Proveedores (nombre, contacto) VALUES (?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.nombre());
            ps.setString(2, p.contacto());
            ps.executeUpdate();
        }
    }

    /* ---------- SELECT por id ---------- */
    public Proveedor find(int id) throws SQLException {
        String sql = "SELECT * FROM Proveedores WHERE id = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        }
    }

    /* ---------- SELECT * ---------- */
    public List<Proveedor> findAll() throws SQLException {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proveedores";

        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) lista.add(mapRow(rs));
        }
        return lista;
    }

    /* ---------- Mapeo ---------- */
    private Proveedor mapRow(ResultSet rs) throws SQLException {
        return new Proveedor(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("contacto"));
    }
}
