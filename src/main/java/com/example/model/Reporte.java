package com.example.model;

import java.time.LocalDateTime;

public class Reporte {
    private Long id;                    // id_reporte
    private Long idMascota;             // FK a mascotas
    private Long idUsuario;             // FK a usuarios (autor del reporte)
    private String estado;              // perdida, encontrada o en_adopcion
    private String barrio;
    private Double latitud;
    private Double longitud;
    private LocalDateTime fechaReporte;
    private String contactoPublico;     // medio de contacto autorizado
    private String estadoPublicacion;   // pendiente, activo, cerrado o moderado

    public Reporte() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdMascota() { return idMascota; }
    public void setIdMascota(Long idMascota) { this.idMascota = idMascota; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getBarrio() { return barrio; }
    public void setBarrio(String barrio) { this.barrio = barrio; }
    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }
    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
    public LocalDateTime getFechaReporte() { return fechaReporte; }
    public void setFechaReporte(LocalDateTime fechaReporte) { this.fechaReporte = fechaReporte; }
    public String getContactoPublico() { return contactoPublico; }
    public void setContactoPublico(String contactoPublico) { this.contactoPublico = contactoPublico; }
    public String getEstadoPublicacion() { return estadoPublicacion; }
    public void setEstadoPublicacion(String estadoPublicacion) { this.estadoPublicacion = estadoPublicacion; }

    @Override
    public String toString() {
        return "Reporte [id=" + id + ", estado=" + estado + ", barrio=" + barrio + "]";
    }
}
