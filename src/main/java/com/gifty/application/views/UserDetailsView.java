package com.gifty.application.views;

import com.gifty.application.config.MessageUtil;
import com.gifty.application.data.user.User;
import com.gifty.application.data.user.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;

@Route(value = "profile", layout = MainLayout.class)
@PermitAll
public class UserDetailsView extends VerticalLayout {

    // Form components
    private final TextField nameField;
    private final TextField lastnameField;
    private final EmailField emailField;

    public UserDetailsView(UserService userService) {
        User user = userService.getAuthenticatedUser();

        // Styles form
        Div contentDiv = new Div();
        contentDiv.setWidth("400px");
        contentDiv.getStyle().set("margin", "0 auto");

        // Edit form
        FormLayout formLayout = new FormLayout();

        nameField = new TextField(MessageUtil.getMessage("text.formName"));
        lastnameField = new TextField(MessageUtil.getMessage("text.formLastname"));
        emailField = new EmailField(MessageUtil.getMessage("text.formEmail"));
        emailField.setReadOnly(true);

        nameField.setValue(user.getName());
        lastnameField.setValue(user.getLastname());
        emailField.setValue(user.getEmail());

        Button saveButton = new Button(MessageUtil.getMessage("button.save"), e -> {
                user.setName(nameField.getValue());
                user.setLastname(lastnameField.getValue());

                userService.save(user);

                Notification.show(MessageUtil.getMessage("notification.updatedUser"), 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        saveButton.getStyle().set("background-color", "green");
        saveButton.getStyle().set("color", "white");

        formLayout.add(nameField, lastnameField, emailField, saveButton);

        // One column
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );

        contentDiv.add(formLayout);
        add(contentDiv);
    }

    private void cancelEdit() {
        UI.getCurrent().navigate(GiftRegistriesView.class);
    }
}
