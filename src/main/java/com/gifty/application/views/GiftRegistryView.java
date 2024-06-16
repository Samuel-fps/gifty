package com.gifty.application.views;

import com.gifty.application.data.gift.Gift;
import com.gifty.application.data.giftRegistry.GiftRegistryService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@PageTitle("Gifty")
@Route(value = "gift-registry", layout = MainLayout.class)
@PermitAll
public class GiftRegistryView extends VerticalLayout implements HasUrlParameter<String> {

    private final GiftRegistryService giftRegistryService;
    private final Grid<Gift> grid;
    private UUID giftRegistryId;

    @Autowired
    public GiftRegistryView(GiftRegistryService giftRegistryService){
        this.giftRegistryService = giftRegistryService;
        this.grid = new Grid<>(Gift.class);

        // New gift button
        Button addGiftButton = new Button("Añadir Nuevo Regalo", e -> {
            if (giftRegistryId != null) {
                UI.getCurrent().navigate(NewGiftView.class, giftRegistryId.toString());
            } else {
                Notification.show("La lista seleccionada no existe, actualiza la página",
                        5000, Notification.Position.BOTTOM_START);
            }
        });

        // Grid columns
        grid.setColumns("name", "price", "state", "person", "url");

        // Click event on grid
        grid.addItemClickListener(event -> {
            Gift selectedGift = event.getItem();
            UI.getCurrent().navigate("edit-gift/" + selectedGift.getId() + "/" + giftRegistryId);
        });

        add(addGiftButton, grid);

        //refreshGrid();

        // Back link
        RouterLink backLink = new RouterLink("Volver", GiftRegistriesView.class);
        add(backLink);
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        UUID giftRegistryId = UUID.fromString(parameter);
        setGiftRegistryId(giftRegistryId);
        refreshGrid();
    }

    public void setGiftRegistryId(UUID id){
        giftRegistryId = id;
    }

    private void refreshGrid() {
        if (giftRegistryId != null) {
            grid.setItems(giftRegistryService.getGiftRegistryById(giftRegistryId).getGifts());
        } else {
            grid.setItems();
        }
    }



}