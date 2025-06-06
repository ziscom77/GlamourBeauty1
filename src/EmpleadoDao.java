import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDao {

    public void save(Empleado e) throws SQLException {
        String sql = "INSERT INTO Empleados(nombre, depto) VALUES(?, ?)";
        try (Connection co = DatabaseConnection.getConnection();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, e.nombre());
            ps.setString(2, e.depto());
            ps.executeUpdate();
        }
    }

    public Empleado find(int id) throws SQLException {
        String sql = "SELECT * FROM Empleados WHERE id = ?";
        try (Connection co = DatabaseConnection.getConnection();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }

    public List<Empleado> findAll() throws SQLException {
        List<Empleado> list = new ArrayList<>();
        try (Connection co = DatabaseConnection.getConnection();
             ResultSet rs = co.createStatement().executeQuery("SELECT * FROM Empleados")) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public void update(Empleado e) throws SQLException {
        String sql = "UPDATE Empleados SET nombre = ?, depto = ? WHERE id = ?";
        try (Connection co = DatabaseConnection.getConnection();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setString(1, e.nombre());
            ps.setString(2, e.depto());
            ps.setInt   (3, e.id());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Empleados WHERE id = ?";
        try (Connection co = DatabaseConnection.getConnection();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Empleado map(ResultSet rs) throws SQLException {
        return new Empleado(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("depto")
        );
    }
}
