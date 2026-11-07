package com.example.model;


public class FotoReporte {
    private Long id;         // id_foto
    private Long idReporte;  // FK a reportes
    private String urlFoto;  // ubicación (URL) de la imagen

    public FotoReporte() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdReporte() { return idReporte; }
    public void setIdReporte(Long idReporte) { this.idReporte = idReporte; }
    public String getUrlFoto() { return urlFoto; }
    public void setUrlFoto(String urlFoto) { this.urlFoto = urlFoto; }

    @Override
    public String toString() {
        return "FotoReporte [id=" + id + ", idReporte=" + idReporte + "]";
    }
}
