package com.gifty.application.login;

import com.gifty.application.user.UserRepository;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route("register")
@PageTitle("Sign up")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    @Autowired
    public RegistrationView(RegistrationFormBinder registrationFormBinder) {
        RegistrationForm registrationForm = registrationFormBinder.getRegistrationForm();
        // Center the RegistrationForm
        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(registrationForm);

        registrationFormBinder.addBindingAndValidation();
    }
}