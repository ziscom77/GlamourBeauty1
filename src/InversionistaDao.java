import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InversionistaDao {

    public void save(Inversionista i) throws SQLException {
        String sql = "INSERT INTO Inversionistas(nombre, capital) VALUES(?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, i.nombre());
            ps.setDouble(2, i.capital());
            ps.executeUpdate();
        }
    }

    public Inversionista find(int id) throws SQLException {
        String sql = "SELECT * FROM Inversionistas WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }
    public List<Inversionista> findAll() throws SQLException {
        List<Inversionista> out = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery("SELECT * FROM Inversionistas")) {
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }

    public void update(Inversionista i) throws SQLException {
        String sql = "UPDATE Inversionistas SET nombre=?, capital=? WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, i.nombre());
            ps.setDouble(2, i.capital());
            ps.setInt(3, i.id());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM Inversionistas WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Inversionista map(ResultSet rs) throws SQLException {
        return new Inversionista(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("capital"));
    }
}
