package com.gifty.application.views;


import com.gifty.application.data.person.PersonDataProvider;
import com.gifty.application.data.person.Person;
import com.gifty.application.data.person.PersonService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;

import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Route("crud-toolbar")
public class PersonCrudView extends Div {

    private Crud<Person> crud;
    private PersonDataProvider dataProvider;

    private String FIRST_NAME = "firstName";
    private String EDIT_COLUMN = "vaadin-crud-edit-column";

    @Autowired
    private PersonService personService;

    public PersonCrudView() {
        crud = new Crud<>(Person.class, createEditor());

        setupGrid();
        setupDataProvider();
        setupToolbar();

        add(crud);
    }

    private CrudEditor<Person> createEditor() {
        TextField firstName = new TextField("First name");
        FormLayout form = new FormLayout(firstName);

        Binder<Person> binder = new Binder<>(Person.class);
        binder.forField(firstName).asRequired().bind(Person::getName, Person::setName);

        return new BinderCrudEditor<>(binder, form);
    }

    private void setupGrid() {
        Grid<Person> grid = crud.getGrid();

        // Only show these columns (all columns shown by default):
        List<String> visibleColumns = Arrays.asList(FIRST_NAME, EDIT_COLUMN);
        grid.getColumns().forEach(column -> {
            String key = column.getKey();
            if (!visibleColumns.contains(key)) {
                grid.removeColumn(column);
            }
        });

        // Reorder the columns (alphabetical by default)
        grid.setColumnOrder(grid.getColumnByKey(FIRST_NAME),
                grid.getColumnByKey(EDIT_COLUMN));
    }

    private void setupDataProvider() {
        dataProvider = new PersonDataProvider(personService);
        crud.setDataProvider(dataProvider);
        crud.addDeleteListener(
                deleteEvent -> dataProvider.delete(deleteEvent.getItem()));
        crud.addSaveListener(
                saveEvent -> dataProvider.persist(saveEvent.getItem()));
    }

    private void setupToolbar() {
        // tag::snippet[]
        Html total = new Html("<span>Total: <b>" + dataProvider.getPersons().size()
                + "</b> employees</span>");

        Button button = new Button("New employee", VaadinIcon.PLUS.create());
        button.addClickListener(event -> {
            crud.edit(new Person(), Crud.EditMode.NEW_ITEM);
        });
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        crud.setNewButton(button);

        HorizontalLayout toolbar = new HorizontalLayout(total);
        toolbar.setAlignItems(FlexComponent.Alignment.CENTER);
        toolbar.setFlexGrow(1, toolbar);
        toolbar.setSpacing(false);
        crud.setToolbar(toolbar);
        // end::snippet[]
    }

}
