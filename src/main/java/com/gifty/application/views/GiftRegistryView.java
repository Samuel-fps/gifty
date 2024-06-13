package com.gifty.application.views;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;

@PageTitle("Gifty")
@Route(value = "gift-registry", layout = MainLayout.class)
@PermitAll
public class GiftRegistryView extends VerticalLayout implements HasUrlParameter<String> {

    private String listName;

    public GiftRegistryView(){
        add(listName);

        // Agregar un link de retorno a la vista principal
        RouterLink backLink = new RouterLink("Volver a la lista", GiftRegistriesView.class);
        add(backLink);
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        listName = parameter;
    }



}