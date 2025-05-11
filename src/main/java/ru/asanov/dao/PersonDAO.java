package ru.asanov.dao;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.asanov.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_ID = 0;
    private List<Person> people;

    {
        people = new ArrayList<Person>();
        people.add(new Person(1, "Gleb"));
        people.add(new Person(2, "Sasha"));
        people.add(new Person(3, "Maxim"));
    }

    public List<Person> getPeople() {
        return people;
    }

    public Person getPersonById(int id) {
        return people.stream().
                filter(person -> person.getId() == id).findFirst().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_ID);
        people.add(person);
    }

    public void update(int id, Person person) {
        Person personToUpdate = getPersonById(id);
        personToUpdate.setName(person.getName());
    }

    public void delete(int id) {
        people.removeIf(person -> person.getId() == id);
    }
}
