package com.example.views;

import java.time.LocalDateTime;
import java.util.List;

import com.example.dao.MascotaDAO;
import com.example.dao.ReporteDAO;
import com.example.dao.UsuarioDAO;
import com.example.model.Mascota;
import com.example.model.Reporte;
import com.example.model.Usuario;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "reportes", layout = MainLayout.class)
@PageTitle("Reportes | Mascotas Bello")
public class ReportesView extends CrudView<Reporte> {

    // Coordenadas aproximadas del centro de Bello, Antioquia (valor inicial cómodo)
    private static final double LATITUD_BELLO = 6.3373;
    private static final double LONGITUD_BELLO = -75.5579;

    private final MascotaDAO mascotaDAO = new MascotaDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private List<Mascota> mascotas = List.of();
    private List<Usuario> usuarios = List.of();

    private final ComboBox<Mascota> mascota = new ComboBox<>("Mascota");
    private final ComboBox<Usuario> autor = new ComboBox<>("Autor del reporte");
    private final ComboBox<String> estado = new ComboBox<>("Estado de la mascota");
    private final ComboBox<String> estadoPublicacion = new ComboBox<>("Estado de la publicación");
    private final TextField barrio = new TextField("Barrio");
    private final NumberField latitud = new NumberField("Latitud");
    private final NumberField longitud = new NumberField("Longitud");
    private final DateTimePicker fecha = new DateTimePicker("Fecha del reporte");
    private final TextField contacto = new TextField("Contacto público");

    public ReportesView() {
        super("Reportes", "reporte", new ReporteDAO());
        iniciar();
    }

    @Override
    protected void configurarColumnas(Grid<Reporte> grid) {
        grid.addColumn(Reporte::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Reporte::getIdMascota).setHeader("ID mascota").setAutoWidth(true);
        grid.addColumn(Reporte::getIdUsuario).setHeader("ID autor").setAutoWidth(true);
        grid.addColumn(Reporte::getEstado).setHeader("Estado").setAutoWidth(true);
        grid.addColumn(Reporte::getBarrio).setHeader("Barrio").setAutoWidth(true);
        grid.addColumn(Reporte::getLatitud).setHeader("Latitud").setAutoWidth(true);
        grid.addColumn(Reporte::getLongitud).setHeader("Longitud").setAutoWidth(true);
        grid.addColumn(r -> formatear(r.getFechaReporte())).setHeader("Fecha").setAutoWidth(true);
        grid.addColumn(Reporte::getContactoPublico).setHeader("Contacto").setAutoWidth(true);
        grid.addColumn(Reporte::getEstadoPublicacion).setHeader("Publicación").setAutoWidth(true);
    }

    @Override
    protected void construirFormulario(FormLayout form) {
        mascota.setItemLabelGenerator(m -> m.getId() + " - " + (m.getNombre() == null ? "(sin nombre)" : m.getNombre())
                + " (" + m.getTipo() + ")");
        autor.setItemLabelGenerator(u -> u.getId() + " - " + u.getNombre() + " " + u.getApellido());
        estado.setItems("perdida", "encontrada", "en_adopcion");
        estadoPublicacion.setItems("pendiente", "activo", "cerrado", "moderado");
        form.add(mascota, autor, estado, estadoPublicacion, barrio, contacto, latitud, longitud, fecha);
        form.setColspan(fecha, 2);
    }

    @Override
    protected void llenarFormulario(Reporte r) {
        mascotas = mascotaDAO.listar();
        usuarios = usuarioDAO.listar();
        mascota.setItems(mascotas);
        autor.setItems(usuarios);
        if (r == null) {
            mascota.clear();
            autor.clear();
            estado.clear();
            estadoPublicacion.setValue("pendiente");
            barrio.clear();
            contacto.clear();
            latitud.setValue(LATITUD_BELLO);
            longitud.setValue(LONGITUD_BELLO);
            fecha.setValue(LocalDateTime.now().withSecond(0).withNano(0));
        } else {
            seleccionarPorId(mascota, mascotas, r.getIdMascota(), Mascota::getId);
            seleccionarPorId(autor, usuarios, r.getIdUsuario(), Usuario::getId);
            estado.setValue(r.getEstado());
            estadoPublicacion.setValue(r.getEstadoPublicacion());
            barrio.setValue(r.getBarrio());
            contacto.setValue(r.getContactoPublico());
            latitud.setValue(r.getLatitud());
            longitud.setValue(r.getLongitud());
            fecha.setValue(r.getFechaReporte());
        }
    }

    @Override
    protected Reporte leerFormulario(Reporte actual) {
        Reporte r = new Reporte();
        if (actual != null) {
            r.setId(actual.getId());
        }
        r.setIdMascota(elegido(mascota.getValue(), "Mascota").getId());
        r.setIdUsuario(elegido(autor.getValue(), "Autor del reporte").getId());
        r.setEstado(elegido(estado.getValue(), "Estado de la mascota"));
        r.setEstadoPublicacion(elegido(estadoPublicacion.getValue(), "Estado de la publicación"));
        r.setBarrio(texto(barrio.getValue(), "Barrio", 80));
        r.setContactoPublico(texto(contacto.getValue(), "Contacto público", 120));
        r.setLatitud(coordenada(latitud.getValue(), "Latitud", -90, 90));
        r.setLongitud(coordenada(longitud.getValue(), "Longitud", -180, 180));
        r.setFechaReporte(elegido(fecha.getValue(), "Fecha del reporte"));
        return r;
    }

    @Override
    protected Long idDe(Reporte r) {
        return r.getId();
    }
}
