package com.gifty.application.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;

import java.util.UUID;

public class GiftView extends VerticalLayout implements HasUrlParameter<String> {



    @Override
    public void setParameter(BeforeEvent event, String parameter) {

    }
}
