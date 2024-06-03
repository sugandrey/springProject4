package ru.sugandrey.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sugandrey.book.Book;
import ru.sugandrey.dao.BookDao;

@Component
public class NewBookValidator implements Validator {

    private BookDao bookDao;
    private UpdateBookValidator updateBookValidator;

    @Autowired
    public NewBookValidator(final BookDao bookDao, final UpdateBookValidator updateBookValidator) {
        this.bookDao = bookDao;
        this.updateBookValidator = updateBookValidator;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        updateBookValidator.validate(o, errors);
        if (bookDao.checkBookDuplicates(book)) {
            errors.rejectValue("bookName", "", "!!!Эта книга уже есть в библиотеке!!!");
        }
    }
}
