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
import ru.sugandrey.utils.NewBookValidator;
import ru.sugandrey.utils.UpdateBookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookDao bookDao;
    private PersonDao personDao;
    private NewBookValidator newBookValidator;
    private UpdateBookValidator updateBookValidator;

    @Autowired
    public BookController(final BookDao bookDao, final PersonDao personDao, final NewBookValidator newBookValidator, final UpdateBookValidator updateBookValidator) {
        this.bookDao = bookDao;
        this.newBookValidator = newBookValidator;
        this.personDao = personDao;
        this.updateBookValidator = updateBookValidator;

    }

    @GetMapping()
    public String getBookList(Model model) {
        model.addAttribute("books", bookDao.getBookList());
        return "books/bookList";
    }

    @GetMapping("/{id}")
    public String showBook(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDao.showBook(id));
        model.addAttribute("people", personDao.getPersonList());
        model.addAttribute("person", personDao.showPerson(bookDao.showBook(id).getPersonId()));
        return "books/book";
    }

    @PostMapping("/{id}")
    public String addReader(@ModelAttribute("personId") int personId, @PathVariable("id") int bookId) {
        bookDao.addReader(bookId, personId);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/0")
    public String deleteReader(@PathVariable("id") int bookId) {
        bookDao.deleteReader(bookId);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDao.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult result) {
        newBookValidator.validate(book, result);
        if(result.hasErrors()) {
            return "books/new";
        }
        bookDao.createNewBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDao.showBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") Book book, BindingResult result, @PathVariable("id") int id) {
        updateBookValidator.validate(book, result);
        if(result.hasErrors()) {
            return "books/edit";
        }
        bookDao.updateBook(book, id);
        return "redirect:/books";
    }
}
