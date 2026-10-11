package com.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    private final String verde = "#176B4D";
    private final String verdeClaro = "#E8F5EE";
    private final String texto = "#24352D";

    public MainView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        getStyle()
                .set("margin", "0")
                .set("background", "#F7FAF8")
                .set("font-family", "Arial, sans-serif")
                .set("color", texto);

        crearEncabezado();
        crearBienvenida();
        crearCategorias();
        crearInformacion();
        crearPiePagina();
    }

    private void crearEncabezado() {
        HorizontalLayout encabezado = new HorizontalLayout();
        encabezado.setWidthFull();
        encabezado.setAlignItems(Alignment.CENTER);
        encabezado.setJustifyContentMode(
                JustifyContentMode.BETWEEN);
        encabezado.setPadding(true);

        encabezado.getStyle()
                .set("background", verde)
                .set("padding", "18px 6%")
                .set("box-sizing", "border-box")
                .set("flex-wrap", "wrap");

        HorizontalLayout marca = new HorizontalLayout();
        marca.setAlignItems(Alignment.CENTER);
        marca.setSpacing(true);

        Span huella = new Span("🐾");
        huella.getStyle().set("font-size", "30px");

        H2 nombre = new H2("Mascotas Bello");
        nombre.getStyle()
                .set("color", "white")
                .set("font-size", "23px")
                .set("margin", "0");

        marca.add(huella, nombre);

        HorizontalLayout menu = new HorizontalLayout();
        menu.setAlignItems(Alignment.CENTER);
        menu.getStyle().set("flex-wrap", "wrap");

        menu.add(
                crearEnlace("#inicio", "Inicio"),
                crearEnlace("#perdidas", "Perdidas"),
                crearEnlace("#encontradas", "Encontradas"),
                crearEnlace("#adopcion", "Adopción"),
                crearEnlace("#contacto", "Contacto")
        );

        encabezado.add(marca, menu);
        add(encabezado);
    }

    private Anchor crearEnlace(String destino, String nombre) {
        Anchor enlace = new Anchor(destino, nombre);

        enlace.getStyle()
                .set("color", "white")
                .set("text-decoration", "none")
                .set("font-size", "14px")
                .set("font-weight", "bold")
                .set("padding", "6px");

        return enlace;
    }

    private void crearBienvenida() {
        Div bienvenida = new Div();
        bienvenida.setId("inicio");
        bienvenida.setWidthFull();

        bienvenida.getStyle()
                .set("box-sizing", "border-box")
                .set("padding", "75px 8%")
                .set("background",
                        "linear-gradient(120deg, #E8F5EE, #F7FAF8)")
                .set("text-align", "center");

        Span etiqueta = new Span("UNA COMUNIDAD QUE PROTEGE");
        etiqueta.getStyle()
                .set("color", verde)
                .set("font-size", "13px")
                .set("font-weight", "bold")
                .set("letter-spacing", "2px");

        H1 titulo = new H1(
                "Cada mascota merece volver a casa");

        titulo.getStyle()
                .set("font-size", "clamp(30px, 5vw, 48px)")
                .set("line-height", "1.2")
                .set("max-width", "850px")
                .set("margin", "20px auto")
                .set("color", texto);

        Paragraph descripcion = new Paragraph(
                "Ayudamos a conectar a las mascotas con sus familias "
                + "y a encontrar hogares llenos de amor en Bello, Antioquia.");

        descripcion.getStyle()
                .set("font-size", "17px")
                .set("line-height", "1.7")
                .set("max-width", "700px")
                .set("margin", "0 auto 25px")
                .set("color", "#58665E");

        Button reportar = new Button("Reportar una mascota");
        reportar.getStyle()
                .set("background", verde)
                .set("color", "white")
                .set("border-radius", "8px")
                .set("padding", "12px 22px")
                .set("font-weight", "bold");

        reportar.addClickListener(event ->
                Notification.show(
                        "El formulario de reportes se integrará próximamente."
                )
        );

        Button explorar = new Button("Explorar mascotas");
        explorar.getStyle()
                .set("background", "white")
                .set("color", verde)
                .set("border", "1px solid " + verde)
                .set("border-radius", "8px")
                .set("padding", "12px 22px")
                .set("font-weight", "bold");

        explorar.addClickListener(event ->
                Notification.show(
                        "Próximamente podrás explorar los reportes."
                )
        );

        HorizontalLayout botones = new HorizontalLayout(
                reportar, explorar);

        botones.setJustifyContentMode(
                JustifyContentMode.CENTER);
        botones.getStyle().set("flex-wrap", "wrap");

        bienvenida.add(etiqueta, titulo, descripcion, botones);
        add(bienvenida);
    }

