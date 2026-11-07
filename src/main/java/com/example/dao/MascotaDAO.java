package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.db.Conexion;
import com.example.db.DatabaseException;
import com.example.model.Mascota;

public class MascotaDAO implements CrudDAO<Mascota> {

    @Override
    public List<Mascota> listar() {
        String sql = "SELECT id_mascota, nombre, tipo, raza, tamano, descripcion, id_usuario "
                + "FROM mascotas ORDER BY id_mascota";
        List<Mascota> lista = new ArrayList<>();
        try (Connection con = Conexion.obtener();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al listar mascotas", e);
        }
        return lista;
    }

    @Override
    public void insertar(Mascota m) {
        String sql = "INSERT INTO mascotas (nombre, tipo, raza, tamano, descripcion, id_usuario) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.obtener();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getTipo());
            ps.setString(3, m.getRaza());
            ps.setString(4, m.getTamano());
            ps.setString(5, m.getDescripcion());
            ps.setLong(6, m.getIdUsuario());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    m.setId(claves.getLong("id_mascota"));
                }
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al guardar la mascota", e);
        }
    }

    @Override
    public void actualizar(Mascota m) {
        String sql = "UPDATE mascotas SET nombre = ?, tipo = ?, raza = ?, tamano = ?, descripcion = ?, "
                + "id_usuario = ? WHERE id_mascota = ?";
        try (Connection con = Conexion.obtener(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getTipo());
            ps.setString(3, m.getRaza());
            ps.setString(4, m.getTamano());
            ps.setString(5, m.getDescripcion());
            ps.setLong(6, m.getIdUsuario());
            ps.setLong(7, m.getId());
            if (ps.executeUpdate() == 0) {
                throw new DatabaseException("La mascota ya no existe.");
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al actualizar la mascota", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM mascotas WHERE id_mascota = ?";
        try (Connection con = Conexion.obtener(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            if (ps.executeUpdate() == 0) {
                throw new DatabaseException("La mascota ya no existe.");
            }
        } catch (SQLException e) {
            throw DatabaseException.de("No se pudo eliminar la mascota", e);
        }
    }

    private Mascota mapear(ResultSet rs) throws SQLException {
        return new Mascota(
                rs.getLong("id_mascota"),
                rs.getString("nombre"),
                rs.getString("tipo"),
                rs.getString("raza"),
                rs.getString("tamano"),
                rs.getString("descripcion"),
                rs.getLong("id_usuario"));
    }
}
