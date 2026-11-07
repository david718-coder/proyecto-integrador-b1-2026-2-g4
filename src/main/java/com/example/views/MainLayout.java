package com.example.views;

import com.example.MainView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;

public class MainLayout extends AppLayout {

    public MainLayout() {
        H1 titulo = new H1("🐾 Mascotas Bello · Administración");
        titulo.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        SideNav menu = new SideNav();
        menu.addItem(new SideNavItem("Inicio", MainView.class));
        menu.addItem(new SideNavItem("Usuarios", UsuariosView.class));
        menu.addItem(new SideNavItem("Mascotas", MascotasView.class));
        menu.addItem(new SideNavItem("Reportes", ReportesView.class));
        menu.addItem(new SideNavItem("Fotos de reportes", FotosReporteView.class));
        menu.addItem(new SideNavItem("Avistamientos", AvistamientosView.class));

        addToDrawer(menu);
        addToNavbar(new DrawerToggle(), titulo);
    }
}
