package com.gifty.application.views;

import com.gifty.application.data.user.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm login = new LoginForm();

    private final UserService userService;

    @Autowired
    public LoginView(AuthenticationContext authenticationContext, UserService userService) {
        this.userService = userService;

        // style
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Login form
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(this::handleLogin);

        // Register view
        Button signinButton = new Button("Sign in");
        signinButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        signinButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(RegistrationView.class)));

        add(new H1("Gifty"), loginForm, signinButton);
    }

    // Validate credentials
    private void handleLogin(AbstractLogin.LoginEvent event) {
        String email = event.getUsername();
        String password = event.getPassword();

        if (userService.validateCredentials(email, password)) {
            UserDetails userDetails = userService.loadUserByUsername(email);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            getUI().ifPresent(ui -> ui.navigate(""));
        } else {
            Notification.show("Invalid email or password.", 3000, Notification.Position.MIDDLE);
            event.getSource().setError(true);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // Verificar si ya hay un usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UI.getCurrent().navigate(MainView.class);
        }
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }
}