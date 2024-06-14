package com.gifty.application.views;

import com.gifty.application.data.gift.GiftService;
import com.gifty.application.data.giftRegistry.GiftRegistry;
import com.gifty.application.data.giftRegistry.GiftRegistryService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;

import java.util.UUID;

@PageTitle("New Gift")
@Route(value = "new-gift", layout = MainLayout.class)
@PermitAll
public class NewGiftView extends FlexLayout implements HasUrlParameter<String> {

    private final GiftService giftService;
    private final GiftRegistryService giftRegistryService;
    private GiftRegistry giftRegistry;

    public NewGiftView(GiftService giftService, GiftRegistryService giftregistryService) {
        this.giftService = giftService;
        this.giftRegistryService = giftregistryService;

        // Div para contener el formulario con estilos de CSS
        Div contentDiv = new Div();
        contentDiv.setWidth("400px"); // Ancho máximo del formulario
        contentDiv.getStyle().set("margin", "0 auto"); // Centrar horizontalmente

        // Formulario para crear un nuevo regalo
        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField("Nombre");
        TextField urlField = new TextField("URL");
        BigDecimalField priceField = new BigDecimalField("Precio");
        priceField.setSuffixComponent((new Div("€")));

        Button saveButton = new Button("Guardar"); //, e -> save(nameField.getValue(), urlField.getValue(), priceField.getValue()));
        formLayout.add(nameField, urlField, priceField, saveButton);

        // Configurar el FormLayout para una sola columna
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1) // 1 columna cuando el ancho es 0px o más
        );

        contentDiv.add(formLayout);
        add(contentDiv);

        // Back link
        //RouterLink backLink = new RouterLink("Volver", GiftRegistryView.class, giftRegistry.getId().toString());
        //add(backLink);
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (parameter != null) {
            UUID uuid = UUID.fromString(parameter);
            //giftRegistry = giftRegistryService.getById(uuid);
        } else {
            giftRegistry = null;
        }
    }
}

