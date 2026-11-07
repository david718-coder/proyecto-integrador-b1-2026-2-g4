package com.example.views;

import com.example.dao.UsuarioDAO;
import com.example.model.Usuario;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "usuarios", layout = MainLayout.class)
@PageTitle("Usuarios | Mascotas Bello")
public class UsuariosView extends CrudView<Usuario> {

    private final TextField nombre = new TextField("Nombre");
    private final TextField apellido = new TextField("Apellido");
    private final EmailField correo = new EmailField("Correo");
    private final TextField telefono = new TextField("Teléfono");
    private final TextField direccion = new TextField("Dirección");
    private final ComboBox<String> rol = new ComboBox<>("Rol");
    private final ComboBox<String> estado = new ComboBox<>("Estado");

    public UsuariosView() {
        super("Usuarios", "usuario", new UsuarioDAO());
        iniciar();
    }

    @Override
    protected void configurarColumnas(Grid<Usuario> grid) {
        grid.addColumn(Usuario::getId).setHeader("ID").setAutoWidth(true);
        grid.addColumn(Usuario::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Usuario::getApellido).setHeader("Apellido").setAutoWidth(true);
        grid.addColumn(Usuario::getEmail).setHeader("Correo").setAutoWidth(true);
        grid.addColumn(Usuario::getTelefono).setHeader("Teléfono").setAutoWidth(true);
        grid.addColumn(Usuario::getRol).setHeader("Rol").setAutoWidth(true);
        grid.addColumn(Usuario::getEstado).setHeader("Estado").setAutoWidth(true);
        grid.addColumn(u -> formatear(u.getFechaRegistro())).setHeader("Registro").setAutoWidth(true);
    }

    @Override
    protected void construirFormulario(FormLayout form) {
        rol.setItems("admin", "usuario", "refugio");
        estado.setItems("activo", "inactivo", "baneado");
        form.add(nombre, apellido, correo, telefono, direccion, rol, estado);
    }

    @Override
    protected void llenarFormulario(Usuario u) {
        if (u == null) {
            nombre.clear();
            apellido.clear();
            correo.clear();
            telefono.clear();
            direccion.clear();
            rol.setValue("usuario");
            estado.setValue("activo");
        } else {
            nombre.setValue(u.getNombre());
            apellido.setValue(u.getApellido());
            correo.setValue(u.getEmail());
            telefono.setValue(u.getTelefono() == null ? "" : u.getTelefono());
            direccion.setValue(u.getDireccion() == null ? "" : u.getDireccion());
            rol.setValue(u.getRol());
            estado.setValue(u.getEstado());
        }
    }

    @Override
    protected Usuario leerFormulario(Usuario actual) {
        Usuario u = new Usuario(); // el constructor vacío pone fechaRegistro = ahora
        if (actual != null) {
            u.setId(actual.getId());
            u.setFechaRegistro(actual.getFechaRegistro());
        }
        u.setNombre(texto(nombre.getValue(), "Nombre", 80));
        u.setApellido(texto(apellido.getValue(), "Apellido", 80));

        String email = texto(correo.getValue(), "Correo", 120);
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("El correo no tiene un formato válido (ej: nombre@dominio.com).");
        }
        u.setEmail(email);

        String tel = textoOpcional(telefono.getValue(), "Teléfono", 20);
        if (tel != null && !tel.matches("^[0-9+ ]{7,20}$")) {
            throw new IllegalArgumentException("El teléfono solo puede tener números (mínimo 7 dígitos).");
        }
        u.setTelefono(tel);

        u.setDireccion(textoOpcional(direccion.getValue(), "Dirección", 150));
        u.setRol(elegido(rol.getValue(), "Rol"));
        u.setEstado(elegido(estado.getValue(), "Estado"));
        return u;
    }

    @Override
    protected Long idDe(Usuario u) {
        return u.getId();
    }
}
