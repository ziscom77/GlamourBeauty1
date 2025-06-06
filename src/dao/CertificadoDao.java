package dao;

import model.Certificado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CertificadoDao {

    public void save(Certificado c) throws SQLException {
        String sql = "INSERT INTO Certificados(empleado_id, descripcion) VALUES(?, ?)";
        try (Connection co = DatabaseConnection.getConnection();
             PreparedStatement ps = co.prepareStatement(sql)) {
            ps.setInt   (1, c.empleadoId());
            ps.setString(2, c.descripcion());
            ps.executeUpdate();
        }
    }

    public List<Certificado> findAll() throws SQLException {
        List<Certificado> list = new ArrayList<>();
        String sql = "SELECT * FROM Certificados";
        try (Connection co = DatabaseConnection.getConnection();
             ResultSet rs = co.createStatement().executeQuery(sql)) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    private Certificado map(ResultSet rs) throws SQLException {
        return new Certificado(
                rs.getInt("id"),
                rs.getInt("empleado_id"),
                rs.getString("descripcion")
        );
    }
}
