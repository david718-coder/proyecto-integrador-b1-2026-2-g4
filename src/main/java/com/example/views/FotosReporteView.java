package com.example.views;

import java.util.List;

import com.example.dao.FotoReporteDAO;
import com.example.dao.ReporteDAO;
import com.example.model.FotoReporte;
import com.example.model.Reporte;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "fotos", layout = MainLayout.class)
@PageTitle("Fotos de reportes | Mascotas Bello")
public class FotosReporteView extends CrudView<FotoReporte> {

    private final ReporteDAO reporteDAO = new ReporteDAO();
    private List<Reporte> reportes = List.of();

    private final ComboBox<Reporte> reporte = new ComboBox<>("Reporte");
    private final TextField url = new TextField("URL de la fotografía");

    public FotosReporteView() {
        super("Fotos de reportes", "foto", new FotoReporteDAO());
        iniciar();
    }

    @Override
    protected void configurarColumnas(Grid<FotoReporte> grid) {
        grid.addColumn(FotoReporte::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(FotoReporte::getIdReporte).setHeader("ID reporte").setAutoWidth(true);
        grid.addColumn(FotoReporte::getUrlFoto).setHeader("URL de la foto").setAutoWidth(true);
    }

    @Override
    protected void construirFormulario(FormLayout form) {
        reporte.setItemLabelGenerator(r -> "#" + r.getId() + " - " + r.getBarrio() + " (" + r.getEstado() + ")");
        form.add(reporte, url);
        form.setColspan(url, 2);
    }

    @Override
    protected void llenarFormulario(FotoReporte f) {
        reportes = reporteDAO.listar();
        reporte.setItems(reportes);
        if (f == null) {
            reporte.clear();
            url.clear();
        } else {
            seleccionarPorId(reporte, reportes, f.getIdReporte(), Reporte::getId);
            url.setValue(f.getUrlFoto());
        }
    }

    @Override
    protected FotoReporte leerFormulario(FotoReporte actual) {
        FotoReporte f = new FotoReporte();
        if (actual != null) {
            f.setId(actual.getId());
        }
        f.setIdReporte(elegido(reporte.getValue(), "Reporte").getId());
        String direccion = texto(url.getValue(), "URL de la fotografía", 500);
        if (!direccion.startsWith("http://") && !direccion.startsWith("https://")) {
            throw new IllegalArgumentException("La URL debe empezar por http:// o https://");
        }
        f.setUrlFoto(direccion);
        return f;
    }

    @Override
    protected Long idDe(FotoReporte f) {
        return f.getId();
    }
}
