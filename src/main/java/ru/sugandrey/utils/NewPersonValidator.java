package ru.sugandrey.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sugandrey.dao.PersonDao;
import ru.sugandrey.person.Person;

@Component
public class NewPersonValidator implements Validator {

    private PersonDao personDao;
    private static final int BIRTH_YEAR_MIN = 1920;

    @Autowired
    public NewPersonValidator(final PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (person.getFullName().isEmpty()) {
            errors.rejectValue("fullName", "", "!!!ФИО не должно быть пустым!!!");
        }
        if (person.getBirthYear() < BIRTH_YEAR_MIN) {
            errors.rejectValue("birthYear", "", "!!!Дата рождения нереальна!!!");
        }
        if (personDao.checkFIODuplicates(person)) {
            errors.rejectValue("fullName", "", "!!!ФИО уже существует!!!");
        }
    }
}
