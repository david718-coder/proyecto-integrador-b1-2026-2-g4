package com.example.model;


public class Mascota {
    private Long id;            // id_mascota (lo genera la BD)
    private String nombre;      // opcional: puede no conocerse
    private String tipo;        // perro, gato u otro
    private String raza;        // opcional
    private String tamano;      // pequeño, mediano o grande
    private String descripcion; // características físicas
    private Long idUsuario;     // responsable (FK a usuarios)

    public Mascota() {
    }

    public Mascota(Long id, String nombre, String tipo, String raza, String tamano,
            String descripcion, Long idUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.raza = raza;
        this.tamano = tamano;
        this.descripcion = descripcion;
        this.idUsuario = idUsuario;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }
    public String getTamano() { return tamano; }
    public void setTamano(String tamano) { this.tamano = tamano; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    @Override
    public String toString() {
        return "Mascota [id=" + id + ", nombre=" + nombre + ", tipo=" + tipo + "]";
    }
}
