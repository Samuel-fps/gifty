package com.gifty.application.views;



import com.gifty.application.data.person.Person;
import com.gifty.application.data.person.PersonService;
import com.gifty.application.data.user.UserService;
import com.gifty.application.security.SecurityService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "people", layout = MainLayout.class)
@PermitAll
public class PersonGridView extends Div {

    private final SecurityService securityService;
    private final PersonService personService;
    private final UserService userService;
    private final Grid<Person> grid;
    private final TextField nameField;
    private final Button saveButton;

    @Autowired
    public PersonGridView(SecurityService securityService, PersonService personService, UserService userService) {
        this.securityService = securityService;
        this.personService = personService;
        this.userService = userService;
        this.grid = new Grid<>(Person.class);
        this.nameField = new TextField("Name");
        this.saveButton = new Button("Save");

        saveButton.addClickListener(e -> {
            String newName = nameField.getValue();
            if (newName.isEmpty()) {
                Notification notification = Notification.show("Name cant be emty");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            Person person = new Person(newName);
            personService.addPersonToUser(person);
            refreshGrid();
            nameField.clear();
        });

        grid.removeAllColumns();

        // Agregar columna para el nombre editable
        grid.addColumn(new ComponentRenderer<>(person -> {
            TextField nameField = new TextField();
            nameField.setValue(person.getName());
            nameField.setReadOnly(true);

            nameField.addBlurListener(event -> {
                String newName = nameField.getValue();
                person.setName(newName);
                personService.save(person);
                refreshGrid();
                nameField.setReadOnly(true); // Después de editar, volver a establecer el campo de texto como de solo lectura
            });

            nameField.addFocusListener(event -> {
                nameField.setReadOnly(false); // Al hacer clic en el campo, habilitar la edición
            });

            return nameField;
        })).setHeader("Name");


        // Agregar columna para el botón de eliminar
        grid.addComponentColumn(person -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                personService.delete(person);
                refreshGrid();
            });
            return deleteButton;
        }).setHeader("Actions");

        add(nameField, saveButton, grid);
        refreshGrid();
    }

    private void refreshGrid() {
        grid.setItems(userService.getAuthenticatedUser().getPersons());
    }

}
