package com.gifty.application.views;

import com.gifty.application.config.MessageUtil;
import com.gifty.application.security.SecurityService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Height;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Whitespace;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.vaadin.lineawesome.LineAwesomeIcon;
import java.util.Locale;

public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, Component icon, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames(Display.FLEX, Gap.XSMALL, Height.MEDIUM, AlignItems.CENTER, Padding.Horizontal.SMALL,
                    TextColor.BODY);
            link.setRoute(view);

            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
            text.addClassNames(FontWeight.MEDIUM, FontSize.MEDIUM, Whitespace.NOWRAP);

            if (icon != null) {
                link.add(icon);
            }
            link.add(text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

    }

    public MainLayout(SecurityService securityService, @Autowired MessageSource messageSource) {
        this.securityService = securityService;
        addToNavbar(createHeaderContent());
    }

    private Component createHeaderContent() {
        Header header = new Header();
        header.addClassNames(BoxSizing.BORDER, Display.FLEX, FlexDirection.COLUMN, Width.FULL);

        Div layout = new Div();

        layout.addClassNames(Display.FLEX, AlignItems.CENTER, Padding.Horizontal.LARGE);

        H1 appName = new H1("Gifty");
        appName.addClassNames(Margin.Vertical.MEDIUM, Margin.End.AUTO, FontSize.LARGE);
        layout.add(appName);

        if (securityService.getAuthenticatedUser() != null) {
            Button logout = new Button(MessageUtil.getMessage("menu.logout"), click ->
                    securityService.logout());
            layout.add(logout);
        }

        Nav nav = new Nav();
        nav.addClassNames(Display.FLEX, Overflow.AUTO, Padding.Horizontal.MEDIUM, Padding.Vertical.XSMALL);

        UnorderedList list = new UnorderedList();
        list.addClassNames(Display.FLEX, Gap.SMALL, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems()) {
            list.add(menuItem);
        }

        header.add(layout, nav);


        return header;
    }

    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{
                new MenuItemInfo(MessageUtil.getMessage("menu.registry"), LineAwesomeIcon.TH_LIST_SOLID.create(), GiftRegistriesView.class),
                new MenuItemInfo(MessageUtil.getMessage("menu.people"), LineAwesomeIcon.TH_SOLID.create(),        PersonGridView.class),
                new MenuItemInfo(MessageUtil.getMessage("menu.registry"), LineAwesomeIcon.GLOBE_SOLID.create(),   MainView.class),
                //new MenuItemInfo(messageSource.getMessage("menu.registry", null, Locale.getDefault()), LineAwesomeIcon.USER.create(), ),
        };
    }

}
