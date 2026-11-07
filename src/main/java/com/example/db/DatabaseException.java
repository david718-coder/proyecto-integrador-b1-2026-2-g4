package com.example.db;

import java.sql.SQLException;


public class DatabaseException extends RuntimeException {

    public DatabaseException(String mensaje) {
        super(mensaje);
    }

    public DatabaseException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    /** Convierte un SQLException técnico en un mensaje entendible. */
    public static DatabaseException de(String accion, SQLException e) {
        String detalle;
        String estado = e.getSQLState() == null ? "" : e.getSQLState();
        switch (estado) {
            case "23503" -> detalle = "el registro está relacionado con otros datos (llave foránea)";
            case "23505" -> detalle = "ya existe un registro con ese valor único (por ejemplo, el correo)";
            case "23514" -> detalle = "algún valor no cumple las reglas de la tabla";
            case "23502" -> detalle = "falta un campo obligatorio";
            default -> detalle = e.getMessage();
        }
        return new DatabaseException(accion + ": " + detalle, e);
    }
}
