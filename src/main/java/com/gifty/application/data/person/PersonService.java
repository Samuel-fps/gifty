package com.gifty.application.data.person;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
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
}
