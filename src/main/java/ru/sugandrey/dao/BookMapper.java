package ru.sugandrey.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.sugandrey.book.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();
        book.setBookId(resultSet.getInt("book_id"));
        book.setBookName(resultSet.getString("book_name"));
        book.setAuthor(resultSet.getString("author"));
        book.setEditionYear(resultSet.getInt("edition_year"));
        book.setPersonId(resultSet.getInt("person_id"));
        return book;
    }
}
