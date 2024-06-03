package ru.sugandrey.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.sugandrey.person.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {


    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();
        person.setPersonId(resultSet.getInt("person_id"));
        person.setFullName(resultSet.getString("full_name"));
        person.setBirthYear(resultSet.getInt("birth_year"));
        return person;
    }
}
