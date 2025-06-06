package dao;/* dao.CotizacionDao.java */

import java.sql.*;
import java.util.*;

public class CotizacionDao {

    public void upsert(String mercado, double precio) throws SQLException {
        String sql = """
            INSERT INTO Cotizaciones(mercado, precio)
            VALUES (?, ?)
            ON DUPLICATE KEY UPDATE precio = VALUES(precio)
        """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, mercado);
            ps.setDouble(2, precio);
            ps.executeUpdate();
        }
    }

    public Map<String, Double> findAll() throws SQLException {
        Map<String, Double> map = new HashMap<>();
        try (Connection c = DatabaseConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery("SELECT * FROM Cotizaciones")) {
            while (rs.next()) map.put(rs.getString("mercado"), rs.getDouble("precio"));
        }
        return map;
    }
}
