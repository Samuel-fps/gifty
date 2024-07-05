package com.gifty.application.views.gift;

import com.gifty.application.config.MessageUtil;
import com.gifty.application.data.gift.Gift;
import com.gifty.application.data.gift.GiftService;
import com.gifty.application.data.gift.State;
import com.gifty.application.data.giftRegistry.GiftRegistry;
import com.gifty.application.data.giftRegistry.GiftRegistryService;
import com.gifty.application.data.person.Person;
import com.gifty.application.data.person.PersonService;
import com.gifty.application.data.user.UserService;
import com.gifty.application.views.gifRegistry.GiftRegistriesView;
import com.gifty.application.views.layout.MainLayout;
import com.gifty.application.views.login.LoginView;
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

import java.math.BigDecimal;
import java.util.UUID;

@Route(value = "edit-gift", layout = MainLayout.class)
@PermitAll
public class GiftDetailsView extends VerticalLayout implements HasUrlParameter<String> {

    private final GiftService giftService;
    private final GiftRegistryService giftRegistryService;
    private final UserService userService;
    private Gift gift;
    private GiftRegistry giftRegistry;

    // Form components
    private TextField nameField;
    private TextField urlField;
    private BigDecimalField priceField;
    private ComboBox<Person> personComboBox;
    private ComboBox<State> stateComboBox;

    public GiftDetailsView(GiftService giftService, PersonService personService, GiftRegistryService giftRegistryService, UserService userService) {
        this.giftService = giftService;
        this.giftRegistryService = giftRegistryService;
        this.userService = userService;

        if(userService.getAuthenticatedUser() == giftRegistry.getUser()) {
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

            stateComboBox = new ComboBox<>(MessageUtil.getMessage("text.formState"));
            stateComboBox.setItems(State.values());

            Button saveButton = new Button(MessageUtil.getMessage("button.save"), e -> {
                if (gift != null) {
                    BigDecimal oldPrice = gift.getPrice(),
                               totalPrice = giftRegistry.getTotalPrice();

                    gift.setName(nameField.getValue());
                    gift.setUrl(urlField.getValue());
                    gift.setPrice(priceField.getValue());
                    gift.setPerson(personComboBox.getValue());
                    gift.setState(stateComboBox.getValue());

                    totalPrice = totalPrice.add(gift.getPrice()).subtract(oldPrice);
                    giftRegistry.setTotalPrice(totalPrice);

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

            formLayout.add(nameField, urlField, priceField, personComboBox, stateComboBox, saveButton, deleteButton, cancelButton);

            // One column
            formLayout.setResponsiveSteps(
                    new FormLayout.ResponsiveStep("0", 1)
            );

            contentDiv.add(formLayout);
            add(contentDiv);
        }
        else {
            UI.getCurrent().navigate(GiftRegistryView.class);
        }
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
            stateComboBox.setValue(gift.getState());
        } else {
            Notification.show(MessageUtil.getMessage("notification.errorNotExitGift"), 5000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate(GiftRegistryView.class, giftRegistryId.toString());
        }
    }
}

