package com.gifty.application.views;

import com.gifty.application.config.MessageUtil;
import com.gifty.application.data.gift.Gift;
import com.gifty.application.data.giftRegistry.GiftRegistryService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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
        Button addGiftButton = new Button(MessageUtil.getMessage("button.addNewGift"), e -> {
            if (giftRegistryId != null) {
                UI.getCurrent().navigate(NewGiftView.class, giftRegistryId.toString());
            } else {
                Notification.show(MessageUtil.getMessage("error.listNotExist"), 5000, Notification.Position.TOP_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        grid.removeAllColumns();

        // Grid columns
        grid.addColumn(Gift::getName).setHeader(MessageUtil.getMessage("grid.name"));
        grid.addColumn(Gift::getPrice).setHeader(MessageUtil.getMessage("grid.price"));
        grid.addColumn(Gift::getState).setHeader(MessageUtil.getMessage("grid.state"));
        grid.addColumn(Gift::getUrl).setHeader(MessageUtil.getMessage("grid.url"));
        grid.addColumn(gift -> gift.getPerson().getName()).setHeader(MessageUtil.getMessage("grid.person"));

        // Click event on grid
        grid.addItemClickListener(event -> {
            Gift selectedGift = event.getItem();
            if (selectedGift != null) {
                UI.getCurrent().navigate(GiftDetailsView.class, selectedGift.getId().toString() + "/" + giftRegistryId.toString());
            } else {
                Notification.show(MessageUtil.getMessage("error.nameEmpty"), 5000, Notification.Position.TOP_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        //H1 viewTitle = new H1(giftRegistryService.getGiftRegistryById(giftRegistryId).getName());

        add( addGiftButton, grid);

        //refreshGrid();

        // Back link
        RouterLink backLink = new RouterLink(MessageUtil.getMessage("link.back") + giftRegistryId, GiftRegistriesView.class);
        add(backLink);
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (parameter != null) {
            UUID id = UUID.fromString(parameter);
            setGiftRegistryId(id);
            refreshGrid();
        } else {
            Notification.show(MessageUtil.getMessage("notification.registryIdNotValid"), 5000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            UI.getCurrent().navigate(GiftRegistriesView.class);
        }
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