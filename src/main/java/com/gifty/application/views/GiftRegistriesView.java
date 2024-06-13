package com.gifty.application.views;

import com.gifty.application.data.giftRegistry.GiftRegistry;
import com.gifty.application.data.giftRegistry.GiftRegistryRepository;
import com.gifty.application.data.giftRegistry.State;
import com.gifty.application.data.user.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.util.List;

@PageTitle("Gifty")
@Route(value = "gift-registries", layout = MainLayout.class)
@PermitAll
public class GiftRegistriesView extends VerticalLayout {

    private final GiftRegistryRepository giftRegistryRepository;
    private final UserService userService;
    private final MessageSource messageSource;
    private final ListDataProvider<GiftRegistry> dataProvider;

    @Autowired
    public GiftRegistriesView(GiftRegistryRepository giftRegistryRepository, UserService userService, MessageSource messageSource) {
        this.giftRegistryRepository = giftRegistryRepository;
        this.userService = userService;
        this.messageSource = messageSource;

        List<GiftRegistry> registries = giftRegistryRepository.findByUser(userService.getAuthenticatedUser());
        dataProvider = new ListDataProvider<>(registries);

        var grid = new Grid<>(GiftRegistry.class);
        // Formulario de entrada
        TextField newNameField = new TextField("Name");
        Button addButton = new Button("Add", e -> {
            String name = newNameField.getValue();

            // Crear un nuevo objeto GiftRegistry
            GiftRegistry newRegistry = new GiftRegistry();
            newRegistry.setName(name);
            newRegistry.setTotalPrice(BigDecimal.ZERO);
            newRegistry.setState(State.PENDIENTE);
            newRegistry.setUser(userService.getAuthenticatedUser());

            // Guardar en el repositorio
            giftRegistryRepository.save(newRegistry);

            // Actualizar el Grid
            dataProvider.getItems().add(newRegistry);
            dataProvider.refreshAll();
            newNameField.clear();
        });

        // Agregar columna para el nombre editable
        grid.addColumn(new ComponentRenderer<>(person -> {
            TextField nameField = new TextField();
            nameField.setValue(person.getName());
            nameField.setReadOnly(true);

            nameField.addBlurListener(event -> {
                String newName = nameField.getValue();
                person.setName(newName);
                giftRegistryRepository.save(person);
                nameField.setReadOnly(true);
            });

            nameField.addFocusListener(event -> {
                nameField.setReadOnly(false); // Al hacer clic en el campo, habilitar la edición
            });

            return nameField;
        })).setHeader("Name");

        grid.setColumns("totalPrice", "state");
        grid.setDataProvider(dataProvider);

        grid.addItemClickListener(event -> {
            // Obtener el elemento de la fila clicada
            GiftRegistry selectedList = event.getItem();

            // Construir los parámetros de la ruta
            RouteParameters routeParameters = new RouteParameters("name", selectedList.getName());

            // Navegar a la vista de detalle (DetailView) con los parámetros
            UI.getCurrent().navigate(GiftRegistryView.class, selectedList.getName());
        });

        HorizontalLayout formLayout = new HorizontalLayout(newNameField, addButton);
        add(grid, formLayout);
    }
}
