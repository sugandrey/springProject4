package ru.sugandrey.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sugandrey.book.Book;
import ru.sugandrey.dao.BookDao;
import ru.sugandrey.dao.PersonDao;
import ru.sugandrey.person.Person;
import ru.sugandrey.utils.NewPersonValidator;
import ru.sugandrey.utils.UpdatePersonValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PersonDao personDao;
    private NewPersonValidator newPersonValidator;
    private BookDao bookDao;
    private UpdatePersonValidator updatePersonValidator;

    @Autowired
    public PeopleController(final PersonDao personDao, final NewPersonValidator newPersonValidator, final BookDao bookDao, final UpdatePersonValidator updatePersonValidator) {
        this.personDao = personDao;
        this.newPersonValidator = newPersonValidator;
        this.bookDao = bookDao;
        this.updatePersonValidator = updatePersonValidator;
    }

    @GetMapping()
    public String getPersonList(Model model) {
        model.addAttribute("people", personDao.getPersonList());
        return "people/peopleList";
    }

    @GetMapping("/{id}")
    public String showPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDao.showPerson(id));
        model.addAttribute("books", bookDao.getBusyBooks(id));
        return "people/person";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDao.deletePerson(id);
        List<Book> busyBooks = bookDao.getBusyBooks(id);
        if (!busyBooks.isEmpty()) {
            for (Book busyBook : busyBooks) {
                bookDao.deleteReader(busyBook.getBookId());
            }
        }
        return "redirect:/people";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult result) {
        newPersonValidator.validate(person, result);
        if(result.hasErrors()) {
            return "people/new";
        }
        personDao.createNewPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDao.showPerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult result, @PathVariable("id") int id) {
        updatePersonValidator.validate(person, result);
        if(result.hasErrors()) {
            return "people/edit";
        }
        personDao.updatePerson(person, id);
        return "redirect:/people";
    }
}
