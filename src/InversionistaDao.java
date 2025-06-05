import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InversionistaDao {

    public void save(Inversionista inv) throws SQLException {
        String sql = "INSERT INTO Inversionistas (nombre, capital) VALUES (?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, inv.nombre());
            ps.setDouble(2, inv.capital());
            ps.executeUpdate();
        }
    }

    public Inversionista find(int id) throws SQLException {
        String sql = "SELECT * FROM Inversionistas WHERE id = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        }
    }

    public List<Inversionista> findAll() throws SQLException {
        List<Inversionista> lista = new ArrayList<>();
        String sql = "SELECT * FROM Inversionistas";

        try (Connection c = DatabaseConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) lista.add(mapRow(rs));
        }
        return lista;
    }

    private Inversionista mapRow(ResultSet rs) throws SQLException {
        return new Inversionista(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getDouble("capital"));
    }
}
