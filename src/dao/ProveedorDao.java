package dao;

import model.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDao {

    /* CREATE */
    public void save(Proveedor p) throws SQLException {
        String sql = "INSERT INTO Proveedores(nombre, contacto) VALUES(?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.nombre());
            ps.setString(2, p.contacto());
            ps.executeUpdate();
        }
    }

    /* READ */
    public Proveedor find(int id) throws SQLException {
        String sql = "SELECT * FROM Proveedores WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? map(rs) : null;
        }
    }
    public List<Proveedor> findAll() throws SQLException {
        List<Proveedor> out = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery("SELECT * FROM Proveedores")) {
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }

    /* UPDATE */
    public void update(Proveedor p) throws SQLException {
        String sql = "UPDATE Proveedores SET nombre=?, contacto=? WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.nombre());
            ps.setString(2, p.contacto());
            ps.setInt(3, p.id());
            ps.executeUpdate();
        }
    }

    /* DELETE */
    public void delete(int id) throws SQLException {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM Proveedores WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Proveedor map(ResultSet rs) throws SQLException {
        return new Proveedor(rs.getInt("id"), rs.getString("nombre"), rs.getString("contacto"));
    }
}

