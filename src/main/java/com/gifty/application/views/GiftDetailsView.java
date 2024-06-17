package com.gifty.application.views;

import com.gifty.application.config.MessageUtil;
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

import java.util.UUID;

@Route(value = "edit-gift", layout = MainLayout.class)
@PermitAll
public class GiftDetailsView extends VerticalLayout implements HasUrlParameter<String> {

    private final GiftService giftService;
    private final GiftRegistryService giftRegistryService;
    private Gift gift;
    private GiftRegistry giftRegistry;

    // Form components
    private final TextField nameField;
    private final TextField urlField;
    private final BigDecimalField priceField;
    private final ComboBox<Person> personComboBox;

    public GiftDetailsView(GiftService giftService, PersonService personService, GiftRegistryService giftRegistryService) {
        this.giftService = giftService;
        this.giftRegistryService = giftRegistryService;

        // Styles form
        Div contentDiv = new Div();
        contentDiv.setWidth("400px");
        contentDiv.getStyle().set("margin", "0 auto");

        // edit form
        FormLayout formLayout = new FormLayout();

        nameField = new TextField(MessageUtil.getMessage("text.formName"));
        urlField = new TextField(MessageUtil.getMessage("text.formUrl"));
        priceField = new BigDecimalField(MessageUtil.getMessage("text.price"));
        priceField.setSuffixComponent((new Div("€")));

        personComboBox = new ComboBox<>(MessageUtil.getMessage("text.formPerson"));
        personComboBox.setItems(personService.getAllPersons());
        personComboBox.setItemLabelGenerator(Person::getName);

        Button saveButton = new Button(MessageUtil.getMessage("button.save"), e -> {
            if (gift != null) {
                gift.setName(nameField.getValue());
                gift.setUrl(urlField.getValue());
                gift.setPrice(priceField.getValue());
                gift.setPerson(personComboBox.getValue());

                giftService.save(gift);

                Notification.show(MessageUtil.getMessage("notification.updatedGift"), 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                UI.getCurrent().navigate(GiftRegistryView.class, giftRegistry.getId().toString());
            } else {
                Notification.show(MessageUtil.getMessage("notification.errorSaveGift"), 3000, Notification.Position.TOP_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        saveButton.getStyle().set("background-color", "green");
        saveButton.getStyle().set("color", "white");

        Button deleteButton = new Button(MessageUtil.getMessage("button.delete"), e -> {
            if (gift != null) {
                giftService.delete(gift, giftRegistry);
                Notification.show(MessageUtil.getMessage("notification.deletedGift"), 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                UI.getCurrent().navigate(GiftRegistryView.class, giftRegistry.getId().toString());
            } else {
                Notification.show(MessageUtil.getMessage("notification.errorNotExitGift"), 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        deleteButton.getStyle().set("background-color", "#B60000");
        deleteButton.getStyle().set("color", "white");


        Button cancelButton = new Button(MessageUtil.getMessage("button.cancel"), e -> cancelEdit());

        formLayout.add(nameField, urlField, priceField, personComboBox, saveButton, deleteButton, cancelButton);

        // One column
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
    public void setParameter(BeforeEvent event,  @WildcardParameter String parameters) {
        UUID giftId = UUID.fromString(parameters.split("/")[0]);
        UUID giftRegistryId = UUID.fromString(parameters.split("/")[1]);

        setGiftRegistry(giftRegistryId);
        setGift(giftId);

        if (gift != null) {
            nameField.setValue(gift.getName());
            urlField.setValue(gift.getUrl());
            priceField.setValue(gift.getPrice());
            personComboBox.setValue(gift.getPerson());
        } else {
            Notification.show(MessageUtil.getMessage("notification.errorNotExitGift"), 5000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate(GiftRegistryView.class, giftRegistryId.toString());
        }
    }
}

