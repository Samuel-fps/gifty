package com.gifty.application.views;

import com.gifty.application.data.giftRegistry.GiftRegistry;
import com.gifty.application.data.giftRegistry.GiftRegistryRepository;
import com.gifty.application.data.giftRegistry.State;
import com.gifty.application.data.user.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
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

        List<GiftRegistry> registries = giftRegistryRepository.findAllByUser(userService.getAuthenticatedUser());
        dataProvider = new ListDataProvider<>(registries);

        var grid = new Grid<>(GiftRegistry.class);
        // Formulario de entrada
        TextField newNameField = new TextField("Name");


        Button addButton = new Button("Add");

        addButton.addClickListener(e -> {
            String name = newNameField.getValue().trim();
            if (name.isEmpty()) {
                newNameField.setInvalid(true);
                newNameField.setErrorMessage("Name cannot be empty");
            } else {
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
            }
        });

        grid.removeAllColumns();

        // Agregar columna para el precio total
        grid.addColumn(GiftRegistry::getId).setHeader("ID");

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

        // Agregar columna para el estado con ComboBox
        grid.addColumn(new ComponentRenderer<>(person -> {
            ComboBox<State> stateComboBox = new ComboBox<>();
            stateComboBox.setItems(State.values());
            stateComboBox.setValue(person.getState());

            stateComboBox.addValueChangeListener(event -> {
                person.setState(event.getValue());
                giftRegistryRepository.save(person);
                dataProvider.refreshItem(person);
            });

            return stateComboBox;
        })).setHeader("State");

        // Agregar columna para el precio total
        grid.addColumn(GiftRegistry::getTotalPrice).setHeader("Total Price");

        // Agregar columna para el botón de eliminar
        grid.addComponentColumn(person -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                giftRegistryRepository.delete(person);
                dataProvider.getItems().remove(person);
                dataProvider.refreshAll();
            });
            return deleteButton;
        }).setHeader("Actions");

        //grid.setColumns("totalPrice", "state");
        grid.setDataProvider(dataProvider);

        grid.addItemClickListener(event -> {
            // Obtener el elemento de la fila clicada
            GiftRegistry selectedList = event.getItem();

            if (selectedList != null && selectedList.getId() != null) {
                // Navegar a la vista de detalle (DetailView) con los parámetros
                UI.getCurrent().navigate(GiftRegistryView.class, selectedList.getId().toString());
            } else {
                Notification.show("La lista seleccionada no existe, actualiza la página",
                        3000, Notification.Position.BOTTOM_START);
            }
        });

        HorizontalLayout formLayout = new HorizontalLayout(newNameField, addButton);
        add(formLayout, grid);
    }

    private void refreshGrid() {
        dataProvider.refreshAll();
    }
}
