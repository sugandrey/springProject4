package ru.sugandrey.person;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Person {

    private int personId;
    @NotEmpty(message = "ФИО не может быть пустым!!!")
    private String fullName;
    @Min(1)
    private int birthYear;

    public Person(final int personId, final String fullName, final int birthYear) {
        this.personId = personId;
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    public Person() {
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(final int personId) {
        this.personId = personId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(final int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Person person = (Person) obj;
       return this.getFullName().equals(person.getFullName());
    }
}
