package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.db.Conexion;
import com.example.db.DatabaseException;
import com.example.model.Avistamiento;

public class AvistamientoDAO implements CrudDAO<Avistamiento> {

    @Override
    public List<Avistamiento> listar() {
        String sql = "SELECT id_avistamiento, id_reporte, id_usuario, descripcion, barrio, latitud, "
                + "longitud, fecha_avistamiento FROM avistamientos ORDER BY id_avistamiento";
        List<Avistamiento> lista = new ArrayList<>();
        try (Connection con = Conexion.obtener();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al listar avistamientos", e);
        }
        return lista;
    }

    @Override
    public void insertar(Avistamiento a) {
        String sql = "INSERT INTO avistamientos (id_reporte, id_usuario, descripcion, barrio, latitud, "
                + "longitud, fecha_avistamiento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.obtener();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, a.getIdReporte());
            ps.setLong(2, a.getIdUsuario());
            ps.setString(3, a.getDescripcion());
            ps.setString(4, a.getBarrio());
            ps.setDouble(5, a.getLatitud());
            ps.setDouble(6, a.getLongitud());
            ps.setObject(7, a.getFechaAvistamiento());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    a.setId(claves.getLong("id_avistamiento"));
                }
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al guardar el avistamiento", e);
        }
    }

    @Override
    public void actualizar(Avistamiento a) {
        String sql = "UPDATE avistamientos SET id_reporte = ?, id_usuario = ?, descripcion = ?, barrio = ?, "
                + "latitud = ?, longitud = ?, fecha_avistamiento = ? WHERE id_avistamiento = ?";
        try (Connection con = Conexion.obtener(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, a.getIdReporte());
            ps.setLong(2, a.getIdUsuario());
            ps.setString(3, a.getDescripcion());
            ps.setString(4, a.getBarrio());
            ps.setDouble(5, a.getLatitud());
            ps.setDouble(6, a.getLongitud());
            ps.setObject(7, a.getFechaAvistamiento());
            ps.setLong(8, a.getId());
            if (ps.executeUpdate() == 0) {
                throw new DatabaseException("El avistamiento ya no existe.");
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al actualizar el avistamiento", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM avistamientos WHERE id_avistamiento = ?";
        try (Connection con = Conexion.obtener(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            if (ps.executeUpdate() == 0) {
                throw new DatabaseException("El avistamiento ya no existe.");
            }
        } catch (SQLException e) {
            throw DatabaseException.de("No se pudo eliminar el avistamiento", e);
        }
    }

    private Avistamiento mapear(ResultSet rs) throws SQLException {
        Avistamiento a = new Avistamiento();
        a.setId(rs.getLong("id_avistamiento"));
        a.setIdReporte(rs.getLong("id_reporte"));
        a.setIdUsuario(rs.getLong("id_usuario"));
        a.setDescripcion(rs.getString("descripcion"));
        a.setBarrio(rs.getString("barrio"));
        a.setLatitud(rs.getDouble("latitud"));
        a.setLongitud(rs.getDouble("longitud"));
        a.setFechaAvistamiento(rs.getObject("fecha_avistamiento", LocalDateTime.class));
        return a;
    }
}
