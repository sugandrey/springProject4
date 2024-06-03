package ru.sugandrey.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sugandrey.book.Book;

@Component
public class UpdateBookValidator implements Validator {


    @Autowired
    public UpdateBookValidator() {
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        getObjectAndErrors(o, errors);
    }

    void getObjectAndErrors(Object o, Errors errors) {
        Book book = (Book) o;
        if (book.getBookName().isEmpty()) {
            errors.rejectValue("bookName", "", "!!!Название книги не должно быть пустым!!!");
        }
        if (book.getAuthor().isEmpty()) {
            errors.rejectValue("author", "", "!!!ФИО автора не должно быть пустым!!!");
        }
        if (book.getEditionYear() <= 0) {
            errors.rejectValue("editionYear", "", "!!!Год издания должен быть больше 0!!!");
        }
    }
}
