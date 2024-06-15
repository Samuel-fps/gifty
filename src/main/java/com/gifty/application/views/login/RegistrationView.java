package com.gifty.application.views.login;

import com.gifty.application.data.user.User;
import com.gifty.application.data.user.UserService;
import com.gifty.application.registration.OnRegistrationCompleteEvent;
import com.gifty.application.registration.RegistrationForm;
import com.gifty.application.registration.RegistrationFormBinder;
import com.gifty.application.security.VerificationToken;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route("register")
@PageTitle("Sign up")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    @Autowired
    private UserService userService;

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

        // Add login link
        Span loginPrompt = new Span("¿Ya estás registrado? ");
        RouterLink loginLink = new RouterLink("inicia sesión", LoginView.class);
        VerticalLayout loginLayout = new VerticalLayout(loginPrompt, loginLink);
        loginLayout.setAlignItems(Alignment.CENTER);

        add(loginLayout);
    }
}