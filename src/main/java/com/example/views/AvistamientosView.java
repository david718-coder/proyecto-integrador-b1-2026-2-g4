package com.example.views;

import java.time.LocalDateTime;
import java.util.List;

import com.example.dao.AvistamientoDAO;
import com.example.dao.ReporteDAO;
import com.example.dao.UsuarioDAO;
import com.example.model.Avistamiento;
import com.example.model.Reporte;
import com.example.model.Usuario;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "avistamientos", layout = MainLayout.class)
@PageTitle("Avistamientos | Mascotas Bello")
public class AvistamientosView extends CrudView<Avistamiento> {

    private static final double LATITUD_BELLO = 6.3373;
    private static final double LONGITUD_BELLO = -75.5579;

    private final ReporteDAO reporteDAO = new ReporteDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private List<Reporte> reportes = List.of();
    private List<Usuario> usuarios = List.of();

    private final ComboBox<Reporte> reporte = new ComboBox<>("Reporte relacionado");
    private final ComboBox<Usuario> usuario = new ComboBox<>("Quién lo vio");
    private final TextArea descripcion = new TextArea("Descripción del avistamiento");
    private final TextField barrio = new TextField("Barrio");
    private final DateTimePicker fecha = new DateTimePicker("Fecha y hora");
    private final NumberField latitud = new NumberField("Latitud");
    private final NumberField longitud = new NumberField("Longitud");

    public AvistamientosView() {
        super("Avistamientos", "avistamiento", new AvistamientoDAO());
        iniciar();
    }

    @Override
    protected void configurarColumnas(Grid<Avistamiento> grid) {
        grid.addColumn(Avistamiento::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Avistamiento::getIdReporte).setHeader("ID reporte").setAutoWidth(true);
        grid.addColumn(Avistamiento::getIdUsuario).setHeader("ID usuario").setAutoWidth(true);
        grid.addColumn(Avistamiento::getDescripcion).setHeader("Descripción").setAutoWidth(true);
        grid.addColumn(Avistamiento::getBarrio).setHeader("Barrio").setAutoWidth(true);
        grid.addColumn(Avistamiento::getLatitud).setHeader("Latitud").setAutoWidth(true);
        grid.addColumn(Avistamiento::getLongitud).setHeader("Longitud").setAutoWidth(true);
        grid.addColumn(a -> formatear(a.getFechaAvistamiento())).setHeader("Fecha").setAutoWidth(true);
    }

    @Override
    protected void construirFormulario(FormLayout form) {
        reporte.setItemLabelGenerator(r -> "#" + r.getId() + " - " + r.getBarrio() + " (" + r.getEstado() + ")");
        usuario.setItemLabelGenerator(u -> u.getId() + " - " + u.getNombre() + " " + u.getApellido());
        form.add(reporte, usuario, descripcion, barrio, fecha, latitud, longitud);
        form.setColspan(descripcion, 2);
    }

    @Override
    protected void llenarFormulario(Avistamiento a) {
        reportes = reporteDAO.listar();
        usuarios = usuarioDAO.listar();
        reporte.setItems(reportes);
        usuario.setItems(usuarios);
        if (a == null) {
            reporte.clear();
            usuario.clear();
            descripcion.clear();
            barrio.clear();
            latitud.setValue(LATITUD_BELLO);
            longitud.setValue(LONGITUD_BELLO);
            fecha.setValue(LocalDateTime.now().withSecond(0).withNano(0));
        } else {
            seleccionarPorId(reporte, reportes, a.getIdReporte(), Reporte::getId);
            seleccionarPorId(usuario, usuarios, a.getIdUsuario(), Usuario::getId);
            descripcion.setValue(a.getDescripcion());
            barrio.setValue(a.getBarrio());
            latitud.setValue(a.getLatitud());
            longitud.setValue(a.getLongitud());
            fecha.setValue(a.getFechaAvistamiento());
        }
    }

    @Override
    protected Avistamiento leerFormulario(Avistamiento actual) {
        Avistamiento a = new Avistamiento();
        if (actual != null) {
            a.setId(actual.getId());
        }
        a.setIdReporte(elegido(reporte.getValue(), "Reporte relacionado").getId());
        a.setIdUsuario(elegido(usuario.getValue(), "Quién lo vio").getId());
        a.setDescripcion(texto(descripcion.getValue(), "Descripción", 1000));
        a.setBarrio(texto(barrio.getValue(), "Barrio", 80));
        a.setLatitud(coordenada(latitud.getValue(), "Latitud", -90, 90));
        a.setLongitud(coordenada(longitud.getValue(), "Longitud", -180, 180));
        a.setFechaAvistamiento(elegido(fecha.getValue(), "Fecha y hora"));
        return a;
    }

    @Override
    protected Long idDe(Avistamiento a) {
        return a.getId();
    }
}
