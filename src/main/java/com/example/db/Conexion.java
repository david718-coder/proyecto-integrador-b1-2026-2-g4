package com.example.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Conexion {

    private static final Properties CONFIG = cargarArchivo();

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // Si falta el driver, el error se reporta al intentar conectar.
        }
    }

    private Conexion() {
    }

    /** Abre una conexión nueva. Quien la use debe cerrarla (try-with-resources). */
    public static Connection obtener() {
        String url = valor("db.url", "DB_URL");
        String usuario = valor("db.user", "DB_USER");
        String clave = valor("db.password", "DB_PASSWORD");
        try {
            return DriverManager.getConnection(url, usuario, clave);
        } catch (SQLException e) {
            throw DatabaseException.de("No se pudo conectar a la base de datos", e);
        }
    }

    private static Properties cargarArchivo() {
        Properties p = new Properties();
        try (InputStream in = Conexion.class.getResourceAsStream("/db.properties")) {
            if (in != null) {
                p.load(in);
            }
        } catch (IOException e) {
            // Si no se puede leer, se intentará con variables de entorno.
        }
        return p;
    }

    private static String valor(String clave, String variableEntorno) {
        String v = System.getenv(variableEntorno);
        if (v == null || v.isBlank()) {
            v = CONFIG.getProperty(clave);
        }
        if (v == null || v.isBlank()) {
            throw new DatabaseException("Falta configurar la conexión (" + clave
                    + "). Copia db.properties.example a db.properties y completa los datos.");
        }
        return v.trim();
    }
}
