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
import com.example.model.Usuario;

public class UsuarioDAO implements CrudDAO<Usuario> {

    @Override
    public List<Usuario> listar() {
        String sql = "SELECT id_usuario, nombre, apellido, correo, telefono, direccion, rol, estado, "
                + "fecha_registro FROM usuarios ORDER BY id_usuario";
        List<Usuario> lista = new ArrayList<>();
        try (Connection con = Conexion.obtener();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al listar usuarios", e);
        }
        return lista;
    }

    @Override
    public void insertar(Usuario u) {
        String sql = "INSERT INTO usuarios (nombre, apellido, correo, telefono, direccion, rol, estado, "
                + "fecha_registro) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.obtener();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getDireccion());
            ps.setString(6, u.getRol());
            ps.setString(7, u.getEstado());
            ps.setObject(8, u.getFechaRegistro());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    u.setId(claves.getLong("id_usuario"));
                }
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al guardar el usuario", e);
        }
    }

    @Override
    public void actualizar(Usuario u) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, correo = ?, telefono = ?, "
                + "direccion = ?, rol = ?, estado = ? WHERE id_usuario = ?";
        try (Connection con = Conexion.obtener(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getDireccion());
            ps.setString(6, u.getRol());
            ps.setString(7, u.getEstado());
            ps.setLong(8, u.getId());
            if (ps.executeUpdate() == 0) {
                throw new DatabaseException("El usuario ya no existe.");
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al actualizar el usuario", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (Connection con = Conexion.obtener(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            if (ps.executeUpdate() == 0) {
                throw new DatabaseException("El usuario ya no existe.");
            }
        } catch (SQLException e) {
            throw DatabaseException.de("No se pudo eliminar el usuario", e);
        }
    }

    /** Convierte la fila actual del ResultSet en un objeto Usuario (mapeo manual). */
    private Usuario mapear(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getLong("id_usuario"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("correo"),
                rs.getString("telefono"),
                rs.getString("direccion"),
                rs.getString("rol"),
                rs.getString("estado"),
                rs.getObject("fecha_registro", LocalDateTime.class));
    }
}
