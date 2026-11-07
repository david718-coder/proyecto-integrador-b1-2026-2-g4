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
import com.example.model.Reporte;

public class ReporteDAO implements CrudDAO<Reporte> {

    @Override
    public List<Reporte> listar() {
        String sql = "SELECT id_reporte, id_mascota, id_usuario, estado, barrio, latitud, longitud, "
                + "fecha_reporte, contacto_publico, estado_publicacion FROM reportes ORDER BY id_reporte";
        List<Reporte> lista = new ArrayList<>();
        try (Connection con = Conexion.obtener();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al listar reportes", e);
        }
        return lista;
    }

    @Override
    public void insertar(Reporte r) {
        String sql = "INSERT INTO reportes (id_mascota, id_usuario, estado, barrio, latitud, longitud, "
                + "fecha_reporte, contacto_publico, estado_publicacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.obtener();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, r.getIdMascota());
            ps.setLong(2, r.getIdUsuario());
            ps.setString(3, r.getEstado());
            ps.setString(4, r.getBarrio());
            ps.setDouble(5, r.getLatitud());
            ps.setDouble(6, r.getLongitud());
            ps.setObject(7, r.getFechaReporte());
            ps.setString(8, r.getContactoPublico());
            ps.setString(9, r.getEstadoPublicacion());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    r.setId(claves.getLong("id_reporte"));
                }
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al guardar el reporte", e);
        }
    }

    @Override
    public void actualizar(Reporte r) {
        String sql = "UPDATE reportes SET id_mascota = ?, id_usuario = ?, estado = ?, barrio = ?, "
                + "latitud = ?, longitud = ?, fecha_reporte = ?, contacto_publico = ?, "
                + "estado_publicacion = ? WHERE id_reporte = ?";
        try (Connection con = Conexion.obtener(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, r.getIdMascota());
            ps.setLong(2, r.getIdUsuario());
            ps.setString(3, r.getEstado());
            ps.setString(4, r.getBarrio());
            ps.setDouble(5, r.getLatitud());
            ps.setDouble(6, r.getLongitud());
            ps.setObject(7, r.getFechaReporte());
            ps.setString(8, r.getContactoPublico());
            ps.setString(9, r.getEstadoPublicacion());
            ps.setLong(10, r.getId());
            if (ps.executeUpdate() == 0) {
                throw new DatabaseException("El reporte ya no existe.");
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al actualizar el reporte", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM reportes WHERE id_reporte = ?";
        try (Connection con = Conexion.obtener(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            if (ps.executeUpdate() == 0) {
                throw new DatabaseException("El reporte ya no existe.");
            }
        } catch (SQLException e) {
            throw DatabaseException.de("No se pudo eliminar el reporte", e);
        }
    }

    private Reporte mapear(ResultSet rs) throws SQLException {
        Reporte r = new Reporte();
        r.setId(rs.getLong("id_reporte"));
        r.setIdMascota(rs.getLong("id_mascota"));
        r.setIdUsuario(rs.getLong("id_usuario"));
        r.setEstado(rs.getString("estado"));
        r.setBarrio(rs.getString("barrio"));
        r.setLatitud(rs.getDouble("latitud"));
        r.setLongitud(rs.getDouble("longitud"));
        r.setFechaReporte(rs.getObject("fecha_reporte", LocalDateTime.class));
        r.setContactoPublico(rs.getString("contacto_publico"));
        r.setEstadoPublicacion(rs.getString("estado_publicacion"));
        return r;
    }
}
