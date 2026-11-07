package com.example.views;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

import com.example.dao.CrudDAO;
import com.example.db.DatabaseException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public abstract class CrudView<T> extends VerticalLayout {

    protected static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    protected final Grid<T> grid = new Grid<>();

    private final CrudDAO<T> dao;
    private final String titulo;
    private final String entidad;
    private final Dialog dialogo = new Dialog();
    private final FormLayout formulario = new FormLayout();

    private T seleccionado;

    protected CrudView(String titulo, String entidad, CrudDAO<T> dao) {
        this.titulo = titulo;
        this.entidad = entidad;
        this.dao = dao;
        setSizeFull();
    }

    protected abstract void configurarColumnas(Grid<T> grid);

    protected abstract void construirFormulario(FormLayout formulario);

    protected abstract void llenarFormulario(T item);

    protected abstract T leerFormulario(T actual);

    /** Devuelve el id del registro (se usa para eliminar). */
    protected abstract Long idDe(T item);


    protected final void iniciar() {
        H2 encabezado = new H2(titulo);
        encabezado.getStyle().set("margin", "0");

        Button nuevo = new Button("Nuevo " + entidad, e -> abrirFormulario(null));
        nuevo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout barra = new HorizontalLayout(encabezado, nuevo);
        barra.setWidthFull();
        barra.setAlignItems(Alignment.CENTER);
        barra.setJustifyContentMode(JustifyContentMode.BETWEEN);

        configurarColumnas(grid);
        grid.addComponentColumn(this::crearAcciones).setHeader("Acciones").setAutoWidth(true).setFlexGrow(0);
        grid.setSizeFull();

        construirFormulario(formulario);
        formulario.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2));

        Button guardar = new Button("Guardar", e -> guardar());
        guardar.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelar = new Button("Cancelar", e -> dialogo.close());

        dialogo.setWidth("650px");
        dialogo.add(formulario);
        dialogo.getFooter().add(cancelar, guardar);

        add(barra, grid);
        refrescar();
    }

    private HorizontalLayout crearAcciones(T item) {
        Button editar = new Button("Editar", e -> abrirFormulario(item));
        editar.addThemeVariants(ButtonVariant.LUMO_SMALL);

        Button eliminar = new Button("Eliminar", e -> confirmarEliminar(item));
        eliminar.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR);

        return new HorizontalLayout(editar, eliminar);
    }

    // ------------------------------------------------------------------
    // Operaciones CRUD
    // ------------------------------------------------------------------

    /** READ: vuelve a consultar la BD y llena la tabla. */
    protected final void refrescar() {
        try {
            grid.setItems(dao.listar());
        } catch (DatabaseException e) {
            avisar(e.getMessage());
        }
    }

    private void abrirFormulario(T item) {
        seleccionado = item;
        try {
            llenarFormulario(item);
        } catch (DatabaseException e) {
            avisar(e.getMessage());
            return;
        }
        dialogo.setHeaderTitle((item == null ? "Nuevo " : "Editar ") + entidad);
        dialogo.open();
    }


    private void guardar() {
        try {
            T objeto = leerFormulario(seleccionado);
            if (seleccionado == null) {
                dao.insertar(objeto);
            } else {
                dao.actualizar(objeto);
            }
            dialogo.close();
            refrescar();
            Notification.show("Guardado correctamente");
        } catch (IllegalArgumentException | DatabaseException e) {
            avisar(e.getMessage());
        }
    }


    private void confirmarEliminar(T item) {
        ConfirmDialog confirmacion = new ConfirmDialog();
        confirmacion.setHeader("Eliminar " + entidad);
        confirmacion.setText("¿Seguro que quieres eliminar este registro? Esta acción no se puede deshacer.");
        confirmacion.setCancelable(true);
        confirmacion.setCancelText("Cancelar");
        confirmacion.setConfirmText("Eliminar");
        confirmacion.setConfirmButtonTheme("error primary");
        confirmacion.addConfirmListener(e -> {
            try {
                dao.eliminar(idDe(item));
                refrescar();
                Notification.show("Eliminado correctamente");
            } catch (DatabaseException ex) {
                avisar(ex.getMessage());
            }
        });
        confirmacion.open();
    }

    protected static void avisar(String mensaje) {
        Notification.show(mensaje, 5000, Notification.Position.MIDDLE);
    }

    
    protected static String texto(String valor, String campo, int max) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo \"" + campo + "\" es obligatorio.");
        }
        String limpio = valor.trim();
        if (limpio.length() > max) {
            throw new IllegalArgumentException("El campo \"" + campo + "\" no puede superar " + max + " caracteres.");
        }
        return limpio;
    }

    protected static String textoOpcional(String valor, String campo, int max) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        return texto(valor, campo, max);
    }


    protected static <V> V elegido(V valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("Debes seleccionar o completar \"" + campo + "\".");
        }
        return valor;
    }


    protected static double coordenada(Double valor, String campo, double min, double max) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo \"" + campo + "\" es obligatorio.");
        }
        if (valor < min || valor > max) {
            throw new IllegalArgumentException(
                    "El campo \"" + campo + "\" debe estar entre " + min + " y " + max + ".");
        }
        return valor;
    }

    protected static String formatear(LocalDateTime fecha) {
        return fecha == null ? "" : FORMATO_FECHA.format(fecha);
    }

    protected static <E> void seleccionarPorId(ComboBox<E> combo, List<E> items, Long id,
            Function<E, Long> obtenerId) {
        combo.clear();
        if (id == null) {
            return;
        }
        for (E item : items) {
            if (id.equals(obtenerId.apply(item))) {
                combo.setValue(item);
                return;
            }
        }
    }
}
