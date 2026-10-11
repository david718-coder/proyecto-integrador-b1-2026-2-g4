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

    private void crearCategorias() {
        VerticalLayout seccion = new VerticalLayout();
        seccion.setWidthFull();
        seccion.setPadding(true);
        seccion.setSpacing(true);

        seccion.getStyle()
                .set("box-sizing", "border-box")
                .set("padding", "55px 6%");

        H2 titulo = new H2("¿Cómo puedes ayudar?");
        titulo.getStyle()
                .set("text-align", "center")
                .set("width", "100%")
                .set("color", texto)
                .set("margin", "0");

        Paragraph subtitulo = new Paragraph(
                "Cada pequeño gesto puede cambiar la vida de una mascota.");

        subtitulo.getStyle()
                .set("text-align", "center")
                .set("width", "100%")
                .set("color", "#66736B");

        HorizontalLayout tarjetas = new HorizontalLayout();
        tarjetas.setWidthFull();
        tarjetas.setSpacing(true);
        tarjetas.getStyle().set("flex-wrap", "wrap");

        tarjetas.add(
                crearTarjeta(
                        "🐕",
                        "Mascotas perdidas",
                        "Ayuda a difundir los reportes de mascotas "
                        + "que están buscando a sus familias.",
                        "perdidas",
                        "#FCEDEA"
                ),
                crearTarjeta(
                        "🐈",
                        "Mascotas encontradas",
                        "Comparte información de mascotas encontradas "
                        + "para facilitar su regreso a casa.",
                        "encontradas",
                        "#EAF2FC"
                ),
                crearTarjeta(
                        "💚",
                        "Adopción responsable",
                        "Descubre mascotas que necesitan una familia "
                        + "y una nueva oportunidad.",
                        "adopcion",
                        "#E8F5EE"
                )
        );

        seccion.add(titulo, subtitulo, tarjetas);
        add(seccion);
    }

    private Div crearTarjeta(
            String emoji,
            String titulo,
            String descripcion,
            String id,
            String fondo) {

        Div tarjeta = new Div();
        tarjeta.setId(id);

        tarjeta.getStyle()
                .set("background", "white")
                .set("border-radius", "14px")
                .set("padding", "25px")
                .set("box-sizing", "border-box")
                .set("border", "1px solid #E3EBE5")
                .set("box-shadow", "0 5px 18px rgba(0,0,0,0.04)")
                .set("flex", "1 1 230px")
                .set("min-width", "220px");

        Span icono = new Span(emoji);
        icono.getStyle()
                .set("display", "inline-flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("background", fondo)
                .set("width", "65px")
                .set("height", "65px")
                .set("border-radius", "14px")
                .set("font-size", "32px");

        H3 encabezado = new H3(titulo);
        encabezado.getStyle()
                .set("color", texto)
                .set("font-size", "21px")
                .set("margin", "20px 0 10px");

        Paragraph detalle = new Paragraph(descripcion);
        detalle.getStyle()
                .set("color", "#66736B")
                .set("line-height", "1.6");

        Button boton = new Button("Ver más");
        boton.getStyle()
                .set("background", verdeClaro)
                .set("color", verde)
                .set("font-weight", "bold")
                .set("border-radius", "7px");

        boton.addClickListener(event ->
                Notification.show(
                        "La sección de " + titulo
                        + " se conectará próximamente."
                )
        );

        tarjeta.add(icono, encabezado, detalle, boton);
        return tarjeta;
    }

    private void crearInformacion() {
        Div informacion = new Div();
        informacion.setId("contacto");
        informacion.setWidthFull();

        informacion.getStyle()
                .set("background", "#E8F5EE")
                .set("padding", "45px 8%")
                .set("box-sizing", "border-box")
                .set("text-align", "center");

        H2 titulo = new H2(
                "Juntos podemos hacer la diferencia");

        titulo.getStyle()
                .set("color", verde)
                .set("margin-top", "0");

        Paragraph descripcion = new Paragraph(
                "Mascotas Bello busca centralizar la información "
                + "de animales perdidos, encontrados y en adopción "
                + "para facilitar la colaboración de la comunidad.");

        descripcion.getStyle()
                .set("max-width", "750px")
                .set("margin", "0 auto")
                .set("line-height", "1.8")
                .set("color", texto);

        informacion.add(titulo, descripcion);
        add(informacion);
    }

    private void crearPiePagina() {
        Footer pie = new Footer();
        pie.setWidthFull();

        Paragraph textoPie = new Paragraph(
                "© 2026 Mascotas Bello · Bello, Antioquia, Colombia");

        textoPie.getStyle()
                .set("color", "white")
                .set("text-align", "center")
                .set("margin", "0");

        pie.add(textoPie);

        pie.getStyle()
                .set("background", "#124B37")
                .set("padding", "22px")
                .set("box-sizing", "border-box");

        add(pie);
    }
}