package com.gifty.application.views.gift;

import com.gifty.application.config.MessageUtil;
import com.gifty.application.data.gift.Gift;
import com.gifty.application.data.giftRegistry.GiftRegistryService;
import com.gifty.application.data.user.UserService;
import com.gifty.application.views.gifRegistry.GiftRegistriesView;
import com.gifty.application.views.layout.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
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
    private final UserService userService;
    private final Grid<Gift> grid;
    private UUID giftRegistryId;

    @Autowired
    public GiftRegistryView(GiftRegistryService giftRegistryService, UserService userService){
        this.giftRegistryService = giftRegistryService;
        this.userService = userService;
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
        addGiftButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        grid.removeAllColumns();

        // Grid columns
        grid.addColumn(Gift::getName).setHeader(MessageUtil.getMessage("grid.name"));
        grid.addColumn(Gift::getPrice).setHeader(MessageUtil.getMessage("grid.price"));
        grid.addColumn(gift -> gift.getPerson().getName()).setHeader(MessageUtil.getMessage("grid.person"));
        grid.addColumn(Gift::getState).setHeader(MessageUtil.getMessage("grid.state"));
        grid.addColumn(Gift::getUrl).setHeader(MessageUtil.getMessage("grid.url"));

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

        setSizeFull();
        grid.setHeightFull();

        add(addGiftButton, grid);

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
            if(giftRegistryService.getGiftRegistryById(giftRegistryId).getUser() == userService.getAuthenticatedUser()) {
                grid.setItems(giftRegistryService.getGiftRegistryById(giftRegistryId).getGifts());
            }
            else{
                UI.getCurrent().navigate(GiftRegistryView.class);
            }
        } else {
            grid.setItems();
        }
    }



}