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

