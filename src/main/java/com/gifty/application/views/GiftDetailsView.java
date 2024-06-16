package com.gifty.application.views;

import com.gifty.application.data.gift.Gift;
import com.gifty.application.data.gift.GiftService;
import com.gifty.application.data.giftRegistry.GiftRegistry;
import com.gifty.application.data.giftRegistry.GiftRegistryService;
import com.gifty.application.data.person.Person;
import com.gifty.application.data.person.PersonService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Route(value = "edit-gift", layout = MainLayout.class)
@PermitAll
public class GiftDetailsView extends VerticalLayout implements HasUrlParameter<String> {

    private final GiftService giftService;
    private final GiftRegistryService giftRegistryService;
    private final PersonService personService;
    private Gift gift;
    private GiftRegistry giftRegistry;

    // Componentes del formulario
    private TextField nameField;
    private TextField urlField;
    private BigDecimalField priceField;
    private ComboBox<Person> personComboBox;

    public GiftDetailsView(GiftService giftService, PersonService personService, GiftRegistryService giftRegistryService) {
        this.giftService = giftService;
        this.personService = personService;
        this.giftRegistryService = giftRegistryService;

        // Styles form
        Div contentDiv = new Div();
        contentDiv.setWidth("400px");
        contentDiv.getStyle().set("margin", "0 auto"); 

        // Formulario para editar un regalo existente
        FormLayout formLayout = new FormLayout();

        nameField = new TextField("Nombre");
        urlField = new TextField("URL");
        priceField = new BigDecimalField("Precio");
        priceField.setSuffixComponent((new Div("€")));

        personComboBox = new ComboBox<>("Persona");
        personComboBox.setItems(personService.getAllPersons());
        personComboBox.setItemLabelGenerator(Person::getName);

        Button saveButton = new Button("Guardar", e -> {
            if (gift != null) {
                gift.setName(nameField.getValue());
                gift.setUrl(urlField.getValue());
                gift.setPrice(priceField.getValue());
                gift.setPerson(personComboBox.getValue());

                giftService.save(gift);

                Notification.show("Regalo actualizado", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                UI.getCurrent().navigate(GiftRegistryView.class, giftRegistry.getId().toString());
            } else {
                Notification.show("No se pudo guardar el regalo", 3000, Notification.Position.TOP_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }

        });
        Button cancelButton = new Button("Cancelar", e -> cancelEdit());

        formLayout.add(nameField, urlField, priceField, personComboBox, saveButton, cancelButton);

        // Configurar el FormLayout para una sola columna
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );

        contentDiv.add(formLayout);
        add(contentDiv);
    }

    public void setGiftRegistry(UUID id){
        giftRegistry = giftRegistryService.getGiftRegistryById(id);
    }

    public void setGift(UUID id){
        gift = giftService.getGiftById(id);
    }

    private void cancelEdit() {
        if (giftRegistry != null) {
            UI.getCurrent().navigate(GiftRegistryView.class, giftRegistry.getId().toString());
        } else {
            UI.getCurrent().navigate(GiftRegistriesView.class);
        }
    }

    @Override
    public void setParameter(BeforeEvent event,  @OptionalParameter String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location.getQueryParameters();

        Map<String, List<String>> parametersMap = queryParameters.getParameters();

        UUID giftId = UUID.fromString(parametersMap.get("giftId").getFirst());
        UUID giftRegistryId = UUID.fromString(parametersMap.get("giftRegistryId").getFirst());

        //Notification.show("datos recibidos " + giftId + " y " + giftRegistryId, 5000, Notification.Position.TOP_CENTER);
        //UI.getCurrent().navigate(GiftRegistryView.class, giftRegistryId.toString());

        setGiftRegistry(giftRegistryId);
        setGift(giftId);

        if (gift != null) {
            nameField.setValue(gift.getName());
            urlField.setValue(gift.getUrl());
            priceField.setValue(gift.getPrice());
            personComboBox.setValue(gift.getPerson());
        } else {
            Notification.show("El regalo no existe", 5000, Notification.Position.TOP_CENTER);
            // Redirigir a la vista de registros de regalos con el ID del registro de regalos
            UI.getCurrent().navigate(GiftRegistryView.class, giftRegistryId.toString());
        }
        /*
        if (parameter != null) {
            // Obtener el ID del regalo y del giftRegistry
            String[] params = parameter.split("/");
            if (params.length == 2) {
                UUID giftId = UUID.fromString(params[0]);
                UUID giftRegistryId = UUID.fromString(params[1]);

                setGiftRegistry(giftRegistryId);
                setGift(giftId);

                gift = giftService.getGiftById(giftId);
                if (gift != null) {
                    nameField.setValue(gift.getName());
                    urlField.setValue(gift.getUrl());
                    priceField.setValue(gift.getPrice());
                    personComboBox.setValue(gift.getPerson());
                } else {
                    Notification.show("El regalo no existe", 5000, Notification.Position.TOP_CENTER);
                    UI.getCurrent().navigate(GiftRegistryView.class, giftRegistryId.toString());
                }
            } else {
                // Manejo de error si los parámetros no son válidos
                Notification.show("Parámetros de URL inválidos " + params.length, 5000, Notification.Position.TOP_CENTER);
                UI.getCurrent().navigate(GiftRegistryView.class);
            }
        } else {
            // Manejo de error si no se proporciona un parámetro válido
            Notification.show("ID del regalo no válido", 5000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate(GiftRegistryView.class);
        }*/
    }
}

