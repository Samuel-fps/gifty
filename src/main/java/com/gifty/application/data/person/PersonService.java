package com.gifty.application.data.person;

import com.gifty.application.data.user.User;
import com.gifty.application.data.user.UserRepository;
import com.gifty.application.data.user.UserService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public PersonService(PersonRepository personRepository, UserRepository userRepository, UserService userService) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public Person getPersonById(UUID id){
        return personRepository.findById(id).orElse(null);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public long countPersons(){
        return personRepository.count();
    }

    public void delete(Person person){
        personRepository.delete(person);
    }

    public void save(Person person){
        personRepository.save(person);
    }

    @Transactional
    public void addPersonToUser(Person person) {
        User user = userService.getAuthenticatedUser();
        if (user != null) {
            user.getPersons().add(person);
            userRepository.save(user);
            Notification notification = Notification.show("Person saved " + person.getName());
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else {
            throw new EntityNotFoundException("User not found");
        }
    }
}
