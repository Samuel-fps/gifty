package com.gifty.application.views;

import com.gifty.application.config.MessageUtil;
import com.gifty.application.data.gift.Gift;
import com.gifty.application.data.gift.GiftService;
import com.gifty.application.data.gift.State;
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
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;

import java.util.UUID;

@PageTitle("Gifty")
@Route(value = "new-gift", layout = MainLayout.class)
@PermitAll
public class NewGiftView extends FlexLayout implements HasUrlParameter<String> {

    private final GiftService giftService;
    private final GiftRegistryService giftRegistryService;
    private final PersonService personService;
    private GiftRegistry giftRegistry;

    public NewGiftView(GiftService giftService, GiftRegistryService giftregistryService, PersonService personService) {
        this.giftService = giftService;
        this.giftRegistryService = giftregistryService;
        this.personService = personService;

        // Div form style
        Div contentDiv = new Div();
        contentDiv.setWidth("400px");
        contentDiv.getStyle().set("margin", "0 auto");

        // form new gift
        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField(MessageUtil.getMessage("text.formName"));
        TextField urlField = new TextField(MessageUtil.getMessage("text.formUrl"));
        BigDecimalField priceField = new BigDecimalField(MessageUtil.getMessage("text.price"));
        priceField.setSuffixComponent((new Div("€")));

        ComboBox<Person> personComboBox = new ComboBox<>(MessageUtil.getMessage("text.formPerson"));
        personComboBox.setItems(personService.getAllPersons());
        personComboBox.setItemLabelGenerator(Person::getName);

        Button saveButton = new Button(MessageUtil.getMessage("button.save"), e -> {
            if (nameField.getValue().trim().isEmpty()) { // Name empty
                nameField.setInvalid(true);
                nameField.setErrorMessage(MessageUtil.getMessage("error.nameEmpty"));
            } else {
                Gift newGift = new Gift(nameField.getValue(), urlField.getValue(), priceField.getValue(),
                        State.POR_COMPRAR, personComboBox.getValue());

                // Save gift
                giftService.save(newGift);
                giftregistryService.addGift(giftRegistry, newGift);

                Notification.show(MessageUtil.getMessage("notification.addedGift") + giftRegistry.getName(),
                                5000, Notification.Position.TOP_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                UI.getCurrent().navigate(GiftRegistryView.class, giftRegistry.getId().toString());
            }
        });

        Button cancelButton = new Button(MessageUtil.getMessage("button.cancel"), e ->
            UI.getCurrent().navigate(GiftRegistryView.class, giftRegistry.getId().toString())
        );

        formLayout.add(nameField, urlField, priceField, personComboBox, saveButton, cancelButton);

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );

        contentDiv.add(formLayout);
        add(contentDiv);
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        if (parameter != null) {
            setGiftRegistry(UUID.fromString(parameter));
        } else {
            giftRegistry = null;
        }
    }

    public void setGiftRegistry(UUID uuid){
        giftRegistry = giftRegistryService.getGiftRegistryById(uuid);
    }


}

