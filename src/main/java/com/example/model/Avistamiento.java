package com.example.model;

import java.time.LocalDateTime;


public class Avistamiento {
    private Long id;                       // id_avistamiento
    private Long idReporte;                // FK a reportes
    private Long idUsuario;                // FK a usuarios (quien vio la mascota)
    private String descripcion;
    private String barrio;
    private Double latitud;
    private Double longitud;
    private LocalDateTime fechaAvistamiento;

    public Avistamiento() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdReporte() { return idReporte; }
    public void setIdReporte(Long idReporte) { this.idReporte = idReporte; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getBarrio() { return barrio; }
    public void setBarrio(String barrio) { this.barrio = barrio; }
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
    public LocalDateTime getFechaAvistamiento() { return fechaAvistamiento; }
    public void setFechaAvistamiento(LocalDateTime fechaAvistamiento) { this.fechaAvistamiento = fechaAvistamiento; }

    @Override
    public String toString() {
        return "Avistamiento [id=" + id + ", idReporte=" + idReporte + ", barrio=" + barrio + "]";
    }
}
