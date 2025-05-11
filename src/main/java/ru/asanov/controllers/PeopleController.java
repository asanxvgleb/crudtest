package ru.asanov.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.asanov.dao.PersonDAO;
import ru.asanov.models.Person;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personDAO.getPeople());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.getPersonById(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());

        return "people/new";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person") Person person) {
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.getPersonById(id));
        return "people/edit";
    }

    @PostMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
