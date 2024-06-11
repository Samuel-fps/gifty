package com.gifty.application.views;

import com.gifty.application.security.AuthenticatedUser;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import java.util.Locale;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

    private final AuthenticatedUser authenticatedUser;
    private final MessageSource messageSource;

    @Autowired
    public LoginView(AuthenticatedUser authenticatedUser, MessageSource messageSource) {
        this.authenticatedUser = authenticatedUser;
        this.messageSource = messageSource;

        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));
        configureLoginI18n();
        setForgotPasswordButtonVisible(false);
        setOpened(true);
        }

    private void configureLoginI18n() {
        Locale locale = VaadinService.getCurrentRequest().getLocale();
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle(messageSource.getMessage("login.title", null, locale));
        i18n.getHeader().setDescription(messageSource.getMessage("login.description", null, locale));
        i18n.setAdditionalInformation(messageSource.getMessage("login.additionalInformation", null, locale));
        i18n.getForm().setTitle(messageSource.getMessage("login.formTitle", null, locale));
        i18n.getForm().setUsername(messageSource.getMessage("login.formEmail", null, locale));
        i18n.getForm().setPassword(messageSource.getMessage("login.formPassword", null, locale));
        i18n.getForm().setSubmit(messageSource.getMessage("login.formSubmit", null, locale));
        setI18n(i18n);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (authenticatedUser.get().isPresent()) {
            setOpened(false);
            beforeEnterEvent.forwardTo("");
        }
        setError(beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }
}