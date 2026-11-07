package com.example.views;

import java.util.List;

import com.example.dao.MascotaDAO;
import com.example.dao.UsuarioDAO;
import com.example.model.Mascota;
import com.example.model.Usuario;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "mascotas", layout = MainLayout.class)
@PageTitle("Mascotas | Mascotas Bello")
public class MascotasView extends CrudView<Mascota> {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private List<Usuario> usuarios = List.of();

    private final TextField nombre = new TextField("Nombre (si se conoce)");
    private final ComboBox<String> tipo = new ComboBox<>("Tipo");
    private final TextField raza = new TextField("Raza (si se conoce)");
    private final ComboBox<String> tamano = new ComboBox<>("Tamaño");
    private final ComboBox<Usuario> responsable = new ComboBox<>("Responsable");
    private final TextArea descripcion = new TextArea("Descripción física");

    public MascotasView() {
        super("Mascotas", "mascota", new MascotaDAO());
        iniciar();
    }

    @Override
    protected void configurarColumnas(Grid<Mascota> grid) {
        grid.addColumn(Mascota::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Mascota::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Mascota::getTipo).setHeader("Tipo").setAutoWidth(true);
        grid.addColumn(Mascota::getRaza).setHeader("Raza").setAutoWidth(true);
        grid.addColumn(Mascota::getTamano).setHeader("Tamaño").setAutoWidth(true);
        grid.addColumn(Mascota::getDescripcion).setHeader("Descripción").setAutoWidth(true);
        grid.addColumn(Mascota::getIdUsuario).setHeader("ID responsable").setAutoWidth(true);
    }

    @Override
    protected void construirFormulario(FormLayout form) {
        tipo.setItems("perro", "gato", "otro");
        tamano.setItems("pequeño", "mediano", "grande");
        responsable.setItemLabelGenerator(u -> u.getId() + " - " + u.getNombre() + " " + u.getApellido());
        form.add(nombre, tipo, raza, tamano, responsable, descripcion);
        form.setColspan(descripcion, 2);
        form.setColspan(responsable, 2);
    }

    @Override
    protected void llenarFormulario(Mascota m) {
        usuarios = usuarioDAO.listar(); // puede lanzar DatabaseException; CrudView lo muestra
        responsable.setItems(usuarios);
        if (m == null) {
            nombre.clear();
            tipo.clear();
            raza.clear();
            tamano.clear();
            responsable.clear();
            descripcion.clear();
        } else {
            nombre.setValue(m.getNombre() == null ? "" : m.getNombre());
            tipo.setValue(m.getTipo());
            raza.setValue(m.getRaza() == null ? "" : m.getRaza());
            tamano.setValue(m.getTamano());
            descripcion.setValue(m.getDescripcion() == null ? "" : m.getDescripcion());
            seleccionarPorId(responsable, usuarios, m.getIdUsuario(), Usuario::getId);
        }
    }

    @Override
    protected Mascota leerFormulario(Mascota actual) {
        Mascota m = new Mascota();
        if (actual != null) {
            m.setId(actual.getId());
        }
        m.setNombre(textoOpcional(nombre.getValue(), "Nombre", 80));
        m.setTipo(elegido(tipo.getValue(), "Tipo"));
        m.setRaza(textoOpcional(raza.getValue(), "Raza", 80));
        m.setTamano(elegido(tamano.getValue(), "Tamaño"));
        m.setDescripcion(textoOpcional(descripcion.getValue(), "Descripción", 1000));
        m.setIdUsuario(elegido(responsable.getValue(), "Responsable").getId());
        return m;
    }

    @Override
    protected Long idDe(Mascota m) {
        return m.getId();
    }
}
