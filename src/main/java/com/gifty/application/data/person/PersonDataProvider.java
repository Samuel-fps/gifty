package com.gifty.application.data.person;

import com.gifty.application.data.person.Person;
import com.gifty.application.data.person.PersonService;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PersonDataProvider extends AbstractBackEndDataProvider<Person, Void> {

    private final PersonService personService;

    public List<Person> getPersons() {
        return persons;
    }

    private List<Person> persons = new ArrayList<>();

    public PersonDataProvider(PersonService personService) {
        this.personService = personService;
        this.persons = personService.getAllPersons();
    }

    @Override
    protected Stream<Person> fetchFromBackEnd(Query<Person, Void> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();

        return persons.stream();
    }

    @Override
    protected int sizeInBackEnd(Query<Person, Void> query) {
        return (int)personService.countPersons();
    }

    public void delete(Person person) {
        personService.delete(person);
        refreshAll();
    }

    public void persist(Person person) {
        if (person.getId() == null) {
            // Agregar la persona a la lista
            persons.add(person);
        } else {
            // Encontrar la persona existente en la lista y actualizarla
            for (int i = 0; i < persons.size(); i++) {
                if (Objects.equals(persons.get(i).getId(), person.getId())) {
                    persons.set(i, person);
                    break;
                }
            }
        }
        // Refrescar la interfaz de usuario para reflejar los cambios
        refreshAll();
    }
}
