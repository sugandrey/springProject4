package ru.sugandrey.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.sugandrey.person.Person;

import java.util.List;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPersonList() {
        return jdbcTemplate.query("SELECT * FROM people", new PersonMapper());
    }

    public Person showPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM people WHERE person_id = ?", new Object[]{id}, new PersonMapper())
                           .stream().findAny().orElse(null);
    }

    public void createNewPerson(Person person) {
        jdbcTemplate.update("INSERT INTO people (full_name, birth_year) VALUES (?, ?)", person.getFullName(), person.getBirthYear());
    }

    public void updatePerson(Person updatedPerson, int id) {
        jdbcTemplate.update("UPDATE people SET full_name = ?, birth_year = ? WHERE person_id = ?", updatedPerson.getFullName(),
                updatedPerson.getBirthYear(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM people WHERE person_id = ?", id);
    }

    public boolean checkFIODuplicates(Person person) {
        return getPersonList().contains(person);
    }

}
