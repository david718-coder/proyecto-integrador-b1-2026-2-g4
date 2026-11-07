package com.example.dao;

import java.util.List;

/**
 * Contrato que cumple cada DAO (Data Access Object).
 * Un DAO es la clase que "habla" con la base de datos usando SQL escrito a mano (JDBC).
 * T es el tipo de objeto: Usuario, Mascota, Reporte, etc.
 */
public interface CrudDAO<T> {

    /** READ: trae todos los registros. */
    List<T> listar();

    /** CREATE: inserta un registro nuevo (y le asigna el id que generó la BD). */
    void insertar(T objeto);

    /** UPDATE: modifica el registro que tenga el mismo id. */
    void actualizar(T objeto);

    /** DELETE: borra el registro con ese id. */
    void eliminar(Long id);
}
