package com.gifty.application.views;

import com.gifty.application.data.gift.Gift;
import com.gifty.application.data.giftRegistry.GiftRegistryService;
import com.vaadin.flow.component.grid.Grid;
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

        //add(listName);

        grid.setColumns("name", "price", "state", "person", "url");

        add(grid);
        //refreshGrid();



        // Back link
        RouterLink backLink = new RouterLink("Volver a la lista", GiftRegistriesView.class);
        add(backLink);
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        giftRegistryId = UUID.fromString(parameter);
    }

    private void refreshGrid() {
        grid.setItems(giftRegistryService.getById(giftRegistryId).getGifts());
    }



}