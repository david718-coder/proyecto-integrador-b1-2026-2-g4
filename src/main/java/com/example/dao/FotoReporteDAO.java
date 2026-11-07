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
import com.example.model.FotoReporte;

public class FotoReporteDAO implements CrudDAO<FotoReporte> {

    /** Regla del proyecto: máximo 3 fotografías por reporte. */
    private static final int MAX_FOTOS_POR_REPORTE = 3;

    @Override
    public List<FotoReporte> listar() {
        String sql = "SELECT id_foto, id_reporte, url_foto FROM fotos_reporte ORDER BY id_foto";
        List<FotoReporte> lista = new ArrayList<>();
        try (Connection con = Conexion.obtener();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al listar fotos", e);
        }
        return lista;
    }

    @Override
    public void insertar(FotoReporte f) {
        String sql = "INSERT INTO fotos_reporte (id_reporte, url_foto) VALUES (?, ?)";
        try (Connection con = Conexion.obtener();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            validarLimite(con, f.getIdReporte(), null);
            ps.setLong(1, f.getIdReporte());
            ps.setString(2, f.getUrlFoto());
            ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    f.setId(claves.getLong("id_foto"));
                }
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al guardar la foto", e);
        }
    }

    @Override
    public void actualizar(FotoReporte f) {
        String sql = "UPDATE fotos_reporte SET id_reporte = ?, url_foto = ? WHERE id_foto = ?";
        try (Connection con = Conexion.obtener(); PreparedStatement ps = con.prepareStatement(sql)) {
            validarLimite(con, f.getIdReporte(), f.getId());
            ps.setLong(1, f.getIdReporte());
            ps.setString(2, f.getUrlFoto());
            ps.setLong(3, f.getId());
            if (ps.executeUpdate() == 0) {
                throw new DatabaseException("La foto ya no existe.");
            }
        } catch (SQLException e) {
            throw DatabaseException.de("Error al actualizar la foto", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM fotos_reporte WHERE id_foto = ?";
        try (Connection con = Conexion.obtener(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            if (ps.executeUpdate() == 0) {
                throw new DatabaseException("La foto ya no existe.");
            }
        } catch (SQLException e) {
            throw DatabaseException.de("No se pudo eliminar la foto", e);
        }
    }

    /** Cuenta las fotos que ya tiene el reporte (sin contar la que se está editando). */
    private void validarLimite(Connection con, Long idReporte, Long idFotoActual) throws SQLException {
        String sql = "SELECT COUNT(*) FROM fotos_reporte WHERE id_reporte = ? AND id_foto <> ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, idReporte);
            ps.setLong(2, idFotoActual == null ? -1L : idFotoActual);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) >= MAX_FOTOS_POR_REPORTE) {
                    throw new DatabaseException(
                            "Un reporte solo puede tener hasta " + MAX_FOTOS_POR_REPORTE + " fotografías.");
                }
            }
        }
    }

    private FotoReporte mapear(ResultSet rs) throws SQLException {
        FotoReporte f = new FotoReporte();
        f.setId(rs.getLong("id_foto"));
        f.setIdReporte(rs.getLong("id_reporte"));
        f.setUrlFoto(rs.getString("url_foto"));
        return f;
    }
}
